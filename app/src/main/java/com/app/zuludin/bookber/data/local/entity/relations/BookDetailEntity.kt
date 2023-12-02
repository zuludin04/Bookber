package com.app.zuludin.bookber.data.local.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.data.local.entity.QuoteEntity

data class BookDetailEntity(
    @Embedded
    val bookEntity: BookEntity,

    @Relation(
        parentColumn = "bookId",
        entityColumn = "bookId"
    )
    val quotes: List<QuoteEntity>,

    @Relation(
        parentColumn = "categoryId",
        entityColumn = "categoryId"
    )
    val category: CategoryEntity
)
