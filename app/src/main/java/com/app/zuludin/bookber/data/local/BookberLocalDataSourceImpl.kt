package com.app.zuludin.bookber.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.data.Result.Error
import com.app.zuludin.bookber.data.Result.Success
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.data.local.entity.relations.BookDetailEntity
import com.app.zuludin.bookber.data.local.entity.relations.BookWithQuoteTotal
import com.app.zuludin.bookber.data.local.entity.relations.QuoteDetailEntity
import com.app.zuludin.bookber.data.local.room.BookDao
import com.app.zuludin.bookber.data.local.room.CategoryDao
import com.app.zuludin.bookber.data.local.room.QuoteDao
import com.app.zuludin.bookber.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookberLocalDataSourceImpl @Inject internal constructor(
    private val bookDao: BookDao,
    private val quoteDao: QuoteDao,
    private val categoryDao: CategoryDao,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) : BookberLocalDataSource {
    override fun observeAllBooks(): Flow<List<BookWithQuoteTotal>> {
        return bookDao.observeAllBooks()
    }

    override suspend fun loadBooks(): List<BookWithQuoteTotal> = bookDao.loadBooks()

    override suspend fun loadBookDetail(bookId: String): Result<BookDetailEntity> =
        withContext(dispatcher) {
            try {
                val book = bookDao.loadBookDetail(bookId)
                if (book != null) {
                    return@withContext Success(book)
                } else {
                    return@withContext Error(Exception(""))
                }
            } catch (e: Exception) {
                return@withContext Error(e)
            }
        }

    override suspend fun saveBook(book: BookEntity) = withContext(dispatcher) {
        bookDao.saveBook(book)
    }

    override suspend fun updateBook(book: BookEntity) = withContext<Unit>(dispatcher) {
        bookDao.updateBook(book)
    }

    override suspend fun deleteBookById(bookId: String) = withContext<Unit>(dispatcher) {
        bookDao.deleteBookById(bookId)
    }

    override fun observeAllQuotes(): Flow<List<QuoteEntity>> {
        return quoteDao.observeAllQuotes()
    }

    override suspend fun insertQuotesIntoBooks(quotes: List<QuoteEntity>) =
        withContext(dispatcher) {
            quoteDao.batchUpdate(quotes)
        }

    override fun loadQuotesByBook(bookId: String): LiveData<Result<List<QuoteEntity>>> {
        return quoteDao.loadQuotesByBook(bookId).map {
            Success(it)
        }
    }

    override suspend fun loadCategories(type: Int): List<CategoryEntity> =
        categoryDao.loadCategories(type)

    override suspend fun loadQuoteDetail(quoteId: String): QuoteDetailEntity? =
        quoteDao.loadQuoteDetail(quoteId)

    override suspend fun saveQuote(quote: QuoteEntity) = withContext(dispatcher) {
        quoteDao.saveQuote(quote)
    }

    override suspend fun updateQuote(quote: QuoteEntity) = withContext<Unit>(dispatcher) {
        quoteDao.updateQuote(quote)
    }

    override suspend fun deleteQuoteById(quoteId: String) = withContext<Unit>(dispatcher) {
        quoteDao.deleteQuoteById(quoteId)
    }

    override fun observeCategoryByType(type: Int): Flow<List<CategoryEntity>> {
        return categoryDao.observeCategoryByType(type)
    }

    override suspend fun saveCategory(category: CategoryEntity) = withContext(dispatcher) {
        categoryDao.saveCategory(category)
    }

    override suspend fun deleteCategoryById(categoryId: String) = withContext<Unit>(dispatcher) {
        categoryDao.deleteCategoryById(categoryId)
    }
}