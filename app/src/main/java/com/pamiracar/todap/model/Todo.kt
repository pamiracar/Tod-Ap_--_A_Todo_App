package com.pamiracar.todap.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(

    @ColumnInfo(name = "baslik")
    var baslik : String,

    @ColumnInfo(name = "metin")
    var metin : String,

){
    @PrimaryKey(autoGenerate = true)
    var id = 0
}
