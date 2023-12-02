package com.app.zuludin.bookber.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
@Entity
data class BookEntity(
    @PrimaryKey
    @ColumnInfo(name = "bookId")
    var id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "cover")
    var cover: String = "",

    @ColumnInfo(name = "author")
    var author: String = "",

    @ColumnInfo(name = "categoryId")
    var categoryId: String = "",
) : Parcelable
