package com.app.zuludin.bookber.util

import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.data.local.entity.relations.BookDetailEntity
import com.app.zuludin.bookber.data.local.entity.relations.BookWithQuoteTotal
import com.app.zuludin.bookber.data.local.entity.relations.QuoteDetailEntity
import com.app.zuludin.bookber.domain.model.Book
import com.app.zuludin.bookber.domain.model.BookDetail
import com.app.zuludin.bookber.domain.model.Category
import com.app.zuludin.bookber.domain.model.Quote
import com.app.zuludin.bookber.domain.model.QuoteDetail

fun Quote.toEntity() = QuoteEntity(
    quotes = quote,
    author = author,
    categoryId = categoryId,
    bookId = bookId
)

fun Quote.toEntityUpdate() = QuoteEntity(
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

fun CategoryEntity.toModel() = Category(
    id = id,
    name = category,
    type = type
)

fun Category.toEntity() = CategoryEntity(
    id = id,
    category = name,
    type = type
)

fun BookWithQuoteTotal.toModel() = Book(
    bookId = book.id,
    title = book.title,
    cover = book.cover,
    category = category?.category ?: "",
    totalQuotes = quotes.size,
    author = book.author,
    categoryId = book.categoryId
)

fun BookEntity.toModel() = Book(
    bookId = id,
    title = title,
    cover = cover,
    author = author
)

fun Book.toEntity() = BookEntity(
    title = title,
    cover = cover,
    author = author,
    categoryId = categoryId
)

fun Book.toEntityUpdate() = BookEntity(
    id = bookId,
    title = title,
    cover = cover,
    author = author,
    categoryId = categoryId
)

fun QuoteDetailEntity.toModel() = QuoteDetail(
    quote = quoteEntity.toModel(),
    category = categoryEntity?.toModel(),
    book = bookEntity?.toModel()
)

fun BookDetailEntity.toModel() = BookDetail(
    book = bookEntity.toModel(),
    quotes = quotes.map { it.toModel() },
    category = category?.toModel()
)