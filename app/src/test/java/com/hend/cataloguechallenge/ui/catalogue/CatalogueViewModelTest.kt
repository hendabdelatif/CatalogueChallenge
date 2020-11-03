package com.hend.cataloguechallenge.ui.catalogue

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.hend.cataloguechallenge.MainCoroutinesRule
import com.hend.cataloguechallenge.network.NetworkHelper
import com.hend.cataloguechallenge.network.api.categories.CategoriesListService
import com.hend.cataloguechallenge.network.response.Resource
import com.hend.cataloguechallenge.persistance.dao.CategoryDao
import com.hend.cataloguechallenge.persistance.entities.Category
import com.hend.cataloguechallenge.repository.CatalogueRepository
import com.hend.cataloguechallenge.utils.MockUtil
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CatalogueViewModelTest {

    private lateinit var catalogueViewModel: CatalogueViewModel
    private lateinit var catalogueRepository: CatalogueRepository
    private val categoriesListService: CategoriesListService = mock()
    private val categoryDao: CategoryDao = mock()
    private val networkHelper : NetworkHelper = mock()

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        catalogueRepository = CatalogueRepository(networkHelper, categoriesListService, categoryDao)
        catalogueViewModel = CatalogueViewModel(catalogueRepository)
    }

    @Test
    fun fetchCategoryListTest() = runBlockingTest {
        val mockData = MockUtil.mockCategoryList()
        whenever(categoryDao.loadAllCategoriesFlow().asLiveData().value).thenReturn(mockData)

        val observer: Observer<Resource<List<Category>>> = mock()
        val observerList: Observer<List<Category>> = mock()

        val fetchedData: LiveData<Resource<List<Category>>> =
            catalogueRepository.getCategoriesList().asLiveData()
        fetchedData.observeForever(observer)

        catalogueViewModel.categoriesList
        delay(500L)

        verify(categoryDao, atLeastOnce()).loadAllCategoriesFlow()
        verify(observerList).onChanged(mockData)
        fetchedData.removeObserver(observer)
    }
}