package com.app.zuludin.bookber.data

import androidx.lifecycle.LiveData
import com.app.zuludin.bookber.data.local.BookberLocalDataSource
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.data.local.entity.relations.BookDetailEntity
import com.app.zuludin.bookber.domain.BookberRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class BookberRepositoryImpl(
    private val localSource: BookberLocalDataSource
) : BookberRepository {
    override fun loadBookStore(): LiveData<Result<List<BookEntity>>> {
        return localSource.loadBookStore()
    }

    override fun loadBooksByCategory(categoryId: String): LiveData<Result<List<BookEntity>>> {
        return localSource.loadBooksByCategory(categoryId)
    }

    override suspend fun loadBookDetail(bookId: String): Result<BookDetailEntity> {
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

    override fun loadAllQuotes(): LiveData<Result<List<QuoteEntity>>> {
        return localSource.loadAllQuotes()
    }

    override fun loadQuotesByBook(bookId: String): LiveData<Result<List<QuoteEntity>>> {
        return localSource.loadQuotesByBook(bookId)
    }

    override fun loadQuotesByCategory(categoryId: String): LiveData<Result<List<QuoteEntity>>> {
        return localSource.loadQuotesByCategory(categoryId)
    }

    override suspend fun saveQuote(quote: QuoteEntity) {
        coroutineScope {
            launch { localSource.saveQuote(quote) }
        }
    }

    override suspend fun updateQuote(quote: QuoteEntity) {
        coroutineScope {
            launch { localSource.updateQuote(quote) }
        }
    }

    override suspend fun deleteQuoteById(quoteId: String) {
        coroutineScope {
            launch { localSource.deleteQuoteById(quoteId) }
        }
    }

    override fun loadCategoriesByType(type: Int): LiveData<Result<List<CategoryEntity>>> {
        return localSource.loadCategoriesByType(type)
    }

    override suspend fun saveCategory(category: CategoryEntity) {
        coroutineScope {
            launch { localSource.saveCategory(category) }
        }
    }

    override suspend fun updateCategory(category: CategoryEntity) {
        coroutineScope {
            launch { localSource.updateCategory(category) }
        }
    }

    override suspend fun deleteCategoryById(categoryId: String) {
        coroutineScope {
            launch { localSource.deleteCategoryById(categoryId) }
        }
    }
}