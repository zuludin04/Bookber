package com.app.zuludin.bookber.domain.model

data class Book(
    val bookId: String = "",
    val title: String = "",
    val cover: String = "",
    val category: String = "",
    val author: String = "",
    val categoryId: String = "",
    val totalQuotes: Int = 0
)
