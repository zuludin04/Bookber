package com.app.zuludin.bookber.domain.model

data class Quote(
    var id: String = "",
    val quote: String = "",
    val author: String = "",
    val categoryId: String = "",
    var bookId: String = ""
)
