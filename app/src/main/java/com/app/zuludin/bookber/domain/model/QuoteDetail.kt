package com.app.zuludin.bookber.domain.model

data class QuoteDetail(
    val quote: Quote = Quote(),
    val category: Category? = null,
    val book: Book? = null
)
