package com.hend.cataloguechallenge.repository

import com.hend.cataloguechallenge.network.NetworkHelper
import com.hend.cataloguechallenge.network.api.categories.CategoriesListService
import com.hend.cataloguechallenge.network.response.Resource
import com.hend.cataloguechallenge.network.response.networkBoundResource
import com.hend.cataloguechallenge.persistance.dao.CategoryDao
import com.hend.cataloguechallenge.persistance.entities.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Used to get data from API service and directly save them to database in order to allow working offline
 */
class CatalogueRepository @Inject constructor(private val networkHelper: NetworkHelper,
    private val categoriesListService: CategoriesListService,
    private val categoryDao: CategoryDao
) {

    @ExperimentalCoroutinesApi
    fun getCategoriesList(): Flow<Resource<List<Category>>> {
        return networkBoundResource(
            fetchFromLocal = { categoryDao.loadAllCategoriesFlow() },
            shouldFetchFromRemote = { networkHelper.isNetworkConnected() },
            fetchFromRemote = { categoryDao.deleteCache()
                categoriesListService.fetchCategoriesList() },
            saveRemoteData = { categoryDao.insertCategoryProducts(it) },
            onFetchFailed = { _, _ -> }
        ).flowOn(Dispatchers.IO)
    }

    /**
     * Get Dao to use outside the repository
     */
    fun getCategoryDao() : CategoryDao{
        return categoryDao
    }
}