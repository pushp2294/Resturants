package com.pushp.resturants.room

import android.content.Context
import androidx.room.*
import com.pushp.resturants.model.table.ResturantTableModel

@Database(entities = arrayOf(ResturantTableModel::class), version = 1, exportSchema = false)
abstract class ResturantDatabase : RoomDatabase() {

    abstract val resturantDao : ResturantDao

}