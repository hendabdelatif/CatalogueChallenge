package com.hend.cataloguechallenge.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hend.cataloguechallenge.persistance.dao.CategoryDao
import com.hend.cataloguechallenge.persistance.entities.*

/**
 * Abstract class for Room Database and entities included
 */
@Database(entities = [Category::class, Product::class, Price::class], version = 1, exportSchema = false)
@TypeConverters(value = [TypeResponseConverter::class])
abstract class AppDatabase : RoomDatabase() {
  abstract fun categoryDao(): CategoryDao
}

