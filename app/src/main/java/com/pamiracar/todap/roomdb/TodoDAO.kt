package com.pamiracar.todap.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.pamiracar.todap.model.Todo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable


@Dao
interface TodoDAO {


    @Query("SELECT * FROM Todo")
    fun getAll() : Flowable<List<Todo>>

    @Query("SELECT * FROM TODO WHERE id = :id")
    fun findById(id:Int) : Flowable<Todo>

    @Insert
    fun insert(todo:Todo) : Completable

    @Delete
    fun delete (todo:Todo) : Completable
}