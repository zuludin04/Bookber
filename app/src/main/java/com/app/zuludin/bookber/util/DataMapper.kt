package com.app.zuludin.bookber.util

import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.domain.model.Quote

fun Quote.toEntity() = QuoteEntity(
    id = id,
    quotes = quote,
    author = author,
    categoryId = categoryId,
    bookId = bookId
)

fun QuoteEntity.toModel() = Quote(
    id = id,
    quote = quotes,
    author = author,
    categoryId = categoryId,
    bookId = bookId
)