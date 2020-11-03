package com.hend.cataloguechallenge.persistance.dao

import androidx.room.*
import com.hend.cataloguechallenge.persistance.entities.Category
import com.hend.cataloguechallenge.persistance.entities.Product
import kotlinx.coroutines.flow.Flow

/**
 * Contains all needed database queries and transactions
 */
@Dao
interface CategoryDao {

    /** Categories Transactions **/
    @Query("SELECT * FROM Category")
    fun loadAllCategoriesFlow(): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCategories(categoriesList: List<Category>)

    /** Products Transactions **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: Product)

    @Query("SELECT * FROM Product WHERE name = :name LIMIT 1")
    suspend fun getProductByName(name: String): Product?

    @Transaction
    fun insertCategoryProducts(categoriesList: List<Category>) {
        insertAllCategories(categoriesList)
        categoriesList.forEach { category ->
            category.products.forEach { product ->
                insertProduct(product)
            }
        }
    }

    /** Delete All **/
    @Query("DELETE FROM Category")
    fun deleteCache()

}