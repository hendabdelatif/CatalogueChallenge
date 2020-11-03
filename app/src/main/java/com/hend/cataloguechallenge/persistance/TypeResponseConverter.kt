package com.hend.cataloguechallenge.persistance

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.hend.cataloguechallenge.persistance.entities.Category
import com.hend.cataloguechallenge.persistance.entities.Price
import com.hend.cataloguechallenge.persistance.entities.Product
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject

/**
 * Contains custom objects conversions in order to save them to Room database
 */
@ProvidedTypeConverter
class TypeResponseConverter @Inject constructor(private val moshi: Moshi){

    /** Convert Category Type **/
    @TypeConverter
    fun fromStringToCategoryType(value: String): List<Category>? {
        val listType = Types.newParameterizedType(List::class.java, Category::class.java)
        val adapter: JsonAdapter<List<Category>> = moshi.adapter(listType)
        return adapter.fromJson(value)
    }

  @TypeConverter
  fun fromCategoryType(type: List<Category>): String {
    val listType = Types.newParameterizedType(List::class.java, Category::class.java)
    val adapter: JsonAdapter<List<Category>> = moshi.adapter(listType)
    return adapter.toJson(type)
  }

    /** Convert Product Type **/
    @TypeConverter
    fun fromStringToProductType(value: String): List<Product>? {
        val listType = Types.newParameterizedType(List::class.java, Product::class.java)
        val adapter: JsonAdapter<List<Product>> = moshi.adapter(listType)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromProductType(type: List<Product>): String {
        val listType = Types.newParameterizedType(List::class.java, Product::class.java)
        val adapter: JsonAdapter<List<Product>> = moshi.adapter(listType)
        return adapter.toJson(type)
    }

    /** Convert Price Type **/
    @TypeConverter
    fun fromStringToPriceType(value: String): Price? {
        return Gson().fromJson(value, Price::class.java)
    }

    @TypeConverter
    fun fromPriceType(value: Price): String {
        return Gson().toJson(value)
    }

}
