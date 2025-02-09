package com.pamiracar.todap.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pamiracar.todap.model.Todo


@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDAO() : TodoDAO
}