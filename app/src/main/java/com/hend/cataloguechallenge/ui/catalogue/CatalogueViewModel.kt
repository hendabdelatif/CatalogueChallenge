package com.hend.cataloguechallenge.ui.catalogue

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.hend.cataloguechallenge.network.response.Resource
import com.hend.cataloguechallenge.persistance.entities.Category
import com.hend.cataloguechallenge.repository.CatalogueRepository
import com.hend.cataloguechallenge.ui.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map

/**
 * ViewModel to get categories list from repository
 */
class CatalogueViewModel @ViewModelInject constructor(catalogueRepository: CatalogueRepository)
    : BaseViewModel(), LifecycleObserver {

    @ExperimentalCoroutinesApi
    val categoriesList: LiveData<Resource<List<Category>>> = catalogueRepository.getCategoriesList().map {
        when (it.status) {
            Resource.Status.LOADING -> {
                Resource.loading(null)
            }
            Resource.Status.SUCCESS -> {
                val categoriesListResponse = it.data
                Resource.success(categoriesListResponse)
            }
            Resource.Status.ERROR -> {
                Resource.error(it.message!!, null)
            }
        }
    }.asLiveData(coroutineContext)
}