package com.hend.cataloguechallenge.network.api.categories

import com.hend.cataloguechallenge.network.response.ApiResponse
import com.hend.cataloguechallenge.persistance.entities.Category
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

/**
 * Service API to fetch categories list
 */
interface CategoriesListService {

    @GET(".")
    fun fetchCategoriesList(): Flow<ApiResponse<List<Category>>>
}