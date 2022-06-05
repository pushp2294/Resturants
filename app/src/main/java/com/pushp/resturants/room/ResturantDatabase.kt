package com.pushp.resturants.room

import android.content.Context
import androidx.room.*
import com.pushp.resturants.model.table.ResturantTableModel

@Database(entities = arrayOf(ResturantTableModel::class), version = 1, exportSchema = false)
abstract class ResturantDatabase : RoomDatabase() {

    abstract fun resturantDao() : ResturantDao

    companion object {

        @Volatile
        private var INSTANCE: ResturantDatabase? = null

        fun getDatasetClient(context: Context) : ResturantDatabase {

            INSTANCE?.let { return it }

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, ResturantDatabase::class.java, "Resturant_Database")
                    .fallbackToDestructiveMigration()
                    .build()


                return INSTANCE!!

            }
        }

    }

}