package com.app.zuludin.bookber.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class CategoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "categoryId")
    var id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "category")
    var category: String = "",

    @ColumnInfo(name = "type")
    var type: Int = 0
)
