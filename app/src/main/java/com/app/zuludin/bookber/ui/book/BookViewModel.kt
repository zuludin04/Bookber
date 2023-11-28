package com.app.zuludin.bookber.ui.book

import androidx.lifecycle.ViewModel
import com.app.zuludin.bookber.domain.BookberRepository

class BookViewModel(private val repository: BookberRepository) : ViewModel() {
    fun getBooks() = repository.loadBookStore()
}