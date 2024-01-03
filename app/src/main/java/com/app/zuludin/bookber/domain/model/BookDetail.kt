package com.app.zuludin.bookber.domain.model

data class BookDetail(
    val book: Book,
    val quotes: List<Quote> = emptyList(),
    val category: Category? = null
)
