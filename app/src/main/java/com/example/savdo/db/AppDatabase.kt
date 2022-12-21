package com.example.savdo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.savdo.model.CategoryModel
import com.example.savdo.model.ProductModel

@Database(entities = [CategoryModel::class, ProductModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getProductDao(): ProductDao
    abstract fun getCategoryDao(): CategoryDao

    companion object {
        var INSTANCE: AppDatabase? = null
        fun initDatabase(context: Context) {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java,"savdo3_db").build()
                }

            }
        }

        fun getDatabase(): AppDatabase {
            return INSTANCE!!
        }
    }
}