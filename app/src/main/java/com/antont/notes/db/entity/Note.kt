package com.antont.notes.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "content") var content: String?
){
    constructor(title:String, content: String?) : this(0, title, content)
}