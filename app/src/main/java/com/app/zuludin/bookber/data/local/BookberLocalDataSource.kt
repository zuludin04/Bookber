package com.app.zuludin.bookber.data.local

import androidx.lifecycle.LiveData
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.data.local.entity.BookEntity

interface BookberLocalDataSource {
    fun loadBookStore(): LiveData<Result<List<BookEntity>>>

    fun loadBookDetail(bookId: String): LiveData<Result<BookEntity>>

    suspend fun saveBook(book: BookEntity)

    suspend fun updateBook(book: BookEntity)

    suspend fun deleteBookById(bookId: String)
}