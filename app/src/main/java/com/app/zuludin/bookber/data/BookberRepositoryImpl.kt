package com.app.zuludin.bookber.data

import androidx.lifecycle.LiveData
import com.app.zuludin.bookber.data.local.BookberLocalDataSource
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.data.local.entity.relations.BookDetailEntity
import com.app.zuludin.bookber.data.local.entity.relations.BookWithQuoteTotal
import com.app.zuludin.bookber.data.local.entity.relations.QuoteDetailEntity
import com.app.zuludin.bookber.domain.BookberRepository
import com.app.zuludin.bookber.domain.model.Category
import com.app.zuludin.bookber.domain.model.Quote
import com.app.zuludin.bookber.util.toModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookberRepositoryImpl @Inject constructor(
    private val localSource: BookberLocalDataSource,
) : BookberRepository {
    override fun loadBookStore(): LiveData<Result<List<BookWithQuoteTotal>>> {
        return localSource.loadBookStore()
    }

    override fun loadBookWithQuotes(): LiveData<Result<List<BookWithQuoteTotal>>> {
        return localSource.loadBookWithQuotes()
    }

    override suspend fun loadBooks(): Result<List<BookWithQuoteTotal>> {
        return localSource.loadBooks()
    }

    override suspend fun loadBooksByCategory(categoryId: String): Result<List<BookWithQuoteTotal>> {
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

    override fun observeAllQuotes(): Flow<Result<List<Quote>>> {
        return localSource.observeAllQuotes().map { entity ->
            entity.map(QuoteEntity::toModel)
        }.asResult()
    }

    override fun loadQuotesByBook(bookId: String): LiveData<Result<List<QuoteEntity>>> {
        return localSource.loadQuotesByBook(bookId)
    }

    override suspend fun loadQuotesByCategory(categoryId: String): Result<List<QuoteEntity>> {
        return localSource.loadQuotesByCategory(categoryId)
    }

    override suspend fun loadQuoteDetail(quoteId: String): Result<QuoteDetailEntity> {
        return localSource.loadQuoteDetail(quoteId)
    }

    override suspend fun insertQuotesIntoBooks(quotes: List<QuoteEntity>) {
        coroutineScope {
            launch { localSource.insertQuotesIntoBooks(quotes) }
        }
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

    override fun observeCategoryByType(type: Int): Flow<Result<List<Category>>> {
        return localSource.observeCategoryByType(type).map { entity ->
            entity.map(CategoryEntity::toModel)
        }.asResult()
    }

    override suspend fun loadCategories(type: Int): Result<List<CategoryEntity>> {
        return localSource.loadCategories(type)
    }

    override suspend fun saveCategory(category: CategoryEntity) {
        coroutineScope {
            launch { localSource.saveCategory(category) }
        }
    }

    override suspend fun deleteCategoryById(categoryId: String) {
        coroutineScope {
            launch { localSource.deleteCategoryById(categoryId) }
        }
    }
}