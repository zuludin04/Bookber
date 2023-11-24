package com.app.zuludin.bookber.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class QuoteEntity(
    @PrimaryKey
    @ColumnInfo(name = "quoteId")
    var id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "quotes")
    var quotes: String = "",

    @ColumnInfo(name = "categoryId")
    var categoryId: String = "",

    @ColumnInfo(name = "bookId")
    var bookId: String = "",
)
