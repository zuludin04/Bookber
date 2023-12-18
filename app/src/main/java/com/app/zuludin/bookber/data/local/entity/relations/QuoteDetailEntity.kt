package com.app.zuludin.bookber.data.local.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.data.local.entity.QuoteEntity

data class QuoteDetailEntity(
    @Embedded
    val quoteEntity: QuoteEntity,

    @Relation(
        parentColumn = "bookId",
        entityColumn = "bookId"
    )
    val bookEntity: BookEntity?,

    @Relation(
        parentColumn = "categoryId",
        entityColumn = "categoryId"
    )
    val categoryEntity: CategoryEntity
)
