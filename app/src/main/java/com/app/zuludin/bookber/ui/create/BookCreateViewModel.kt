package com.app.zuludin.bookber.ui.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.domain.BookberRepository
import kotlinx.coroutines.launch

class BookCreateViewModel(private val repository: BookberRepository) : ViewModel() {
    fun getDetailBook(bookId: String) = repository.loadBookDetail(bookId)

    fun saveBook(book: BookEntity) {
        viewModelScope.launch {
            repository.saveBook(book)
        }
    }

    fun updateBook(book: BookEntity) {
        viewModelScope.launch {
            repository.updateBook(book)
        }
    }

    fun saveQuote(quote: QuoteEntity) {
        viewModelScope.launch {
            repository.saveQuote(quote)
        }
    }
}