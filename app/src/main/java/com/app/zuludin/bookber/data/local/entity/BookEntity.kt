package com.app.zuludin.bookber.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class BookEntity(
    @PrimaryKey
    @ColumnInfo(name = "bookId")
    var id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "cover")
    var cover: String = "",

    @ColumnInfo(name = "genre")
    var genre: String = "",

    @ColumnInfo(name = "category")
    var category: String = "",
)
