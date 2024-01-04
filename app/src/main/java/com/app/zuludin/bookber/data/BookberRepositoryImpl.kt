package com.app.zuludin.bookber.data

import androidx.lifecycle.LiveData
import com.app.zuludin.bookber.data.local.BookberLocalDataSource
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.data.local.entity.relations.BookWithQuoteTotal
import com.app.zuludin.bookber.di.DefaultDispatcher
import com.app.zuludin.bookber.domain.BookberRepository
import com.app.zuludin.bookber.domain.model.Book
import com.app.zuludin.bookber.domain.model.BookDetail
import com.app.zuludin.bookber.domain.model.Category
import com.app.zuludin.bookber.domain.model.Quote
import com.app.zuludin.bookber.domain.model.QuoteDetail
import com.app.zuludin.bookber.util.toEntity
import com.app.zuludin.bookber.util.toEntityUpdate
import com.app.zuludin.bookber.util.toModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookberRepositoryImpl @Inject constructor(
    private val localSource: BookberLocalDataSource,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) : BookberRepository {
    override fun observeAllBooks(): Flow<Result<List<Book>>> {
        return localSource.observeAllBooks().map { entity ->
            entity.map(BookWithQuoteTotal::toModel)
        }.asResult()
    }

    override suspend fun loadBooks(): Result<List<Book>> = withContext(dispatcher) {
        try {
            val books = localSource.loadBooks().map { it.toModel() }
            if (books.isNotEmpty()) {
                return@withContext Result.Success(books)
            } else {
                return@withContext Result.Success(emptyList())
            }
        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }

    override suspend fun loadBookDetail(bookId: String): Result<BookDetail> =
        withContext(dispatcher) {
            try {
                val book = localSource.loadBookDetail(bookId)?.toModel()
                if (book != null) {
                    return@withContext Result.Success(book)
                } else {
                    return@withContext Result.Error(Exception(""))
                }
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }

    override suspend fun saveBook(book: Book): String = withContext(dispatcher) {
        return@withContext localSource.saveBook(book.toEntity())
    }

    override suspend fun updateBook(book: Book) {
        coroutineScope {
            launch { localSource.updateBook(book.toEntityUpdate()) }
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

    override suspend fun loadQuoteDetail(quoteId: String): Result<QuoteDetail> =
        withContext(dispatcher) {
            try {
                val book = localSource.loadQuoteDetail(quoteId)?.toModel()
                if (book != null) {
                    return@withContext Result.Success(book)
                } else {
                    return@withContext Result.Error(Exception(""))
                }
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }

    override suspend fun insertQuotesIntoBooks(quotes: List<Quote>) {
        coroutineScope {
            launch { localSource.insertQuotesIntoBooks(quotes.map { it.toEntityUpdate() }) }
        }
    }

    override suspend fun saveQuote(quote: Quote): String = withContext(dispatcher) {
        return@withContext localSource.saveQuote(quote.toEntity())
    }

    override suspend fun updateQuote(quote: Quote) {
        coroutineScope {
            launch { localSource.updateQuote(quote.toEntityUpdate()) }
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

    override suspend fun loadCategories(type: Int): Result<List<Category>> =
        withContext(dispatcher) {
            try {
                val categories = localSource.loadCategories(type).map { it.toModel() }
                if (categories.isNotEmpty()) {
                    return@withContext Result.Success(categories)
                } else {
                    return@withContext Result.Success(emptyList())
                }
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }

    override suspend fun saveCategory(category: Category) {
        coroutineScope {
            launch { localSource.saveCategory(category.toEntity()) }
        }
    }

    override suspend fun deleteCategoryById(categoryId: String) {
        coroutineScope {
            launch { localSource.deleteCategoryById(categoryId) }
        }
    }
}