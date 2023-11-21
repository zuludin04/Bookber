package com.app.zuludin.bookber.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val bookId: String = "",
    val title: String = "",
    val cover: String = "",
    val genre: String = "",
    val category: String = ""
): Parcelable
