package com.app.zuludin.bookber.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.zuludin.bookber.data.local.BookberLocalDataSource
import com.app.zuludin.bookber.data.local.entity.BookEntity

class BookberFakeDataSource(var books: MutableList<BookEntity>? = mutableListOf()) :
    BookberLocalDataSource {
    override fun loadBookStore(): LiveData<Result<List<BookEntity>>> {
        val observableBooks = MutableLiveData<Result<List<BookEntity>>>()
        observableBooks.value = Result.Success(books ?: arrayListOf())
        return observableBooks
    }

    override fun loadBookDetail(bookId: String): LiveData<Result<BookEntity>> {
        val observableBook = MutableLiveData<Result<BookEntity>>()
        observableBook.value = Result.Success(books!![0])
        return observableBook
    }

    override suspend fun saveBook(book: BookEntity) {
        books?.add(book)
    }

    override suspend fun updateBook(book: BookEntity) {
        books?.firstOrNull { it.id == book.id }?.let { it.title = book.title }
    }

    override suspend fun deleteBookById(bookId: String) {
        books?.removeIf { it.id == bookId }
    }
}