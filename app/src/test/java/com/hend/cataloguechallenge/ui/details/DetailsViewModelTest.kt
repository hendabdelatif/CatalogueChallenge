package com.hend.cataloguechallenge.ui.details

import com.hend.cataloguechallenge.persistance.LocalDatabase
import com.hend.cataloguechallenge.persistance.dao.CategoryDao
import com.hend.cataloguechallenge.utils.MockUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Config.OLDEST_SDK])
class DetailsViewModelTest : LocalDatabase() {

    private lateinit var categoryDao: CategoryDao

    @Before
    fun init() {
        categoryDao = db.categoryDao()
    }

    @Test
    fun getProductByNameFromDB() = runBlockingTest {
        val mockDataList = MockUtil.mockCategoryList()
        categoryDao.insertCategoryProducts(mockDataList)

        val job = launch {
            val breadProduct = categoryDao.getProductByName("Bread")
            val mockBreadData = MockUtil.mockBreadProduct()

            val beerProduct = categoryDao.getProductByName("Beer")
            val mockBeerData = MockUtil.mockBeerProduct()

            MatcherAssert.assertThat(
                breadProduct.toString(),
                CoreMatchers.`is`(mockBreadData.toString())
            )
            MatcherAssert.assertThat(
                beerProduct.toString(),
                CoreMatchers.`is`(mockBeerData.toString())
            )
        }
        job.cancel()
    }
}