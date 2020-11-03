package com.hend.cataloguechallenge.persistance.dao

import com.hend.cataloguechallenge.persistance.LocalDatabase
import com.hend.cataloguechallenge.persistance.entities.Category
import com.hend.cataloguechallenge.utils.MockUtil.mockCategory
import com.hend.cataloguechallenge.utils.MockUtil.mockCategoryList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Config.OLDEST_SDK])
class CategoryDaoTest : LocalDatabase() {

    private lateinit var categoryDao: CategoryDao

    @Before
    fun init() {
        categoryDao = db.categoryDao()
    }

    @Test
    fun insertAndLoadCategoryListTest() = runBlockingTest {
        val mockDataList = mockCategoryList()
        categoryDao.insertAllCategories(mockDataList)

        val loadFromDB = categoryDao.loadAllCategoriesFlow()
        val mockData = mockCategory()

        val job = launch {
            loadFromDB.collect { value: List<Category> ->
                MatcherAssert.assertThat(value.toString(), `is`(mockDataList.toString()))
                MatcherAssert.assertThat(value[0].toString(), `is`(mockData.toString()))
            }
        }
        job.cancel()
    }

}