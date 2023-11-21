package com.app.zuludin.bookber.domain

import androidx.lifecycle.LiveData
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.data.local.entity.BookEntity

interface BookberRepository {
    fun loadBookStore(): LiveData<Result<List<BookEntity>>>

    fun loadBookDetail(bookId: String): LiveData<Result<BookEntity>>

    suspend fun saveBook(bookEntity: BookEntity)

    suspend fun updateBook(bookEntity: BookEntity)

    suspend fun deleteBookById(bookId: String)
}