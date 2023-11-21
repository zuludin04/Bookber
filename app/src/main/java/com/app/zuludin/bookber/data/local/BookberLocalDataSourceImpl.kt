package com.app.zuludin.bookber.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.data.Result.Success
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.data.local.room.BookDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookberLocalDataSourceImpl internal constructor(
    private val bookDao: BookDao, private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BookberLocalDataSource {
    override fun loadBookStore(): LiveData<Result<List<BookEntity>>> {
        return bookDao.loadBookStore().map {
            Success(it)
        }
    }

    override fun loadBookDetail(bookId: String): LiveData<Result<BookEntity>> {
        return bookDao.loadBookDetail(bookId).map {
            Success(it)
        }
    }

    override suspend fun saveBook(book: BookEntity) = withContext(ioDispatcher) {
        bookDao.saveBook(book)
    }

    override suspend fun updateBook(book: BookEntity) = withContext<Unit>(ioDispatcher) {
        bookDao.updateBook(book)
    }

    override suspend fun deleteBookById(bookId: String) = withContext<Unit>(ioDispatcher) {
        bookDao.deleteBookById(bookId)
    }
}