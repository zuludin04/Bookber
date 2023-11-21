package com.app.zuludin.bookber.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.domain.BookberRepository
import kotlinx.coroutines.launch

class BookDetailViewModel(private val repository: BookberRepository) : ViewModel() {
    private val _bookId = MutableLiveData<String>()

    private val _book = _bookId.switchMap { bookId ->
        repository.loadBookDetail(bookId).map { computeResult(it) }
    }
    val book: LiveData<BookEntity?> = _book

    fun getDetailBook(bookId: String) = repository.loadBookDetail(bookId)

    fun deleteBook(bookId: String) {
        viewModelScope.launch {
            repository.deleteBookById(bookId)
        }
    }

    fun start(bookId: String?) {
        if (bookId == _bookId.value) {
            return
        }

        _bookId.value = bookId ?: ""
    }

    private fun computeResult(bookResult: Result<BookEntity>): BookEntity? {
        return if (bookResult is Result.Success) {
            bookResult.data
        } else {
            null
        }
    }
}