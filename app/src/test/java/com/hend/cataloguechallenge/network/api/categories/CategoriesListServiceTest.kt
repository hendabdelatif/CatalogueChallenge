package com.hend.cataloguechallenge.network.api.categories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hend.cataloguechallenge.MainCoroutinesRule
import com.hend.cataloguechallenge.network.response.ApiResponse
import com.hend.cataloguechallenge.network.response.ApiSuccessResponse
import com.hend.cataloguechallenge.network.response.calladapter.FlowCallAdapterFactory
import com.hend.cataloguechallenge.persistance.entities.Category
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class CategoriesListServiceTest {

    @Rule
    @JvmField
    val instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    lateinit var mockWebServer: MockWebServer
    private lateinit var service: CategoriesListService

    @Throws(IOException::class)
    @Before
    fun initService() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        service = createService(CategoriesListService::class.java)
    }

    @Test
    fun fetchCategoriesListFromNetworkTest() = runBlockingTest {
        val response = service.fetchCategoriesList()
        val job = launch {
            response.collect { value: ApiResponse<List<Category>> ->
                val responseBody =
                    requireNotNull((value as ApiSuccessResponse<List<Category>>).body)
                MatcherAssert.assertThat(responseBody[0].name, CoreMatchers.`is`("Food"))
                MatcherAssert.assertThat(responseBody[1].name, CoreMatchers.`is`("Drink"))
            }
        }
        job.cancel()
    }

    @Throws(IOException::class)
    @After
    fun stopServer() {
        mockWebServer.shutdown()
    }

    private fun createService(clazz: Class<CategoriesListService>): CategoriesListService {
        return Retrofit.Builder()
            .baseUrl(mockWebServer.url("."))
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(FlowCallAdapterFactory())
            .build()
            .create(clazz)
    }
}