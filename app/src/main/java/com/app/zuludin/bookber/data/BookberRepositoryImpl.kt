package com.app.zuludin.bookber.data

import androidx.lifecycle.LiveData
import com.app.zuludin.bookber.data.local.BookberLocalDataSource
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.domain.BookberRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class BookberRepositoryImpl(
    private val localSource: BookberLocalDataSource
) : BookberRepository {
    override fun loadBookStore(): LiveData<Result<List<BookEntity>>> {
        return localSource.loadBookStore()
    }

    override fun loadBookDetail(bookId: String): LiveData<Result<BookEntity>> {
        return localSource.loadBookDetail(bookId)
    }

    override suspend fun saveBook(bookEntity: BookEntity) {
        coroutineScope {
            launch { localSource.saveBook(bookEntity) }
        }
    }

    override suspend fun updateBook(bookEntity: BookEntity) {
        coroutineScope {
            launch { localSource.updateBook(bookEntity) }
        }
    }

    override suspend fun deleteBookById(bookId: String) {
        coroutineScope {
            launch { localSource.deleteBookById(bookId) }
        }
    }
}