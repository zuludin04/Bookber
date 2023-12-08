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
import com.app.zuludin.bookber.data.local.room.BookDao
import com.app.zuludin.bookber.data.local.room.CategoryDao
import com.app.zuludin.bookber.data.local.room.QuoteDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookberLocalDataSourceImpl internal constructor(
    private val bookDao: BookDao,
    private val quoteDao: QuoteDao,
    private val categoryDao: CategoryDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BookberLocalDataSource {
    override fun loadBookStore(): LiveData<Result<List<BookEntity>>> {
        return bookDao.loadBookStore().map {
            Success(it)
        }
    }

    override fun loadBooksByCategory(categoryId: String): LiveData<Result<List<BookEntity>>> {
        return bookDao.loadBooksByCategory(categoryId).map {
            Success(it)
        }
    }

    override suspend fun loadBookDetail(bookId: String): Result<BookDetailEntity> =
        withContext(ioDispatcher) {
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

    override suspend fun saveBook(book: BookEntity) = withContext(ioDispatcher) {
        bookDao.saveBook(book)
    }

    override suspend fun updateBook(book: BookEntity) = withContext<Unit>(ioDispatcher) {
        bookDao.updateBook(book)
    }

    override suspend fun deleteBookById(bookId: String) = withContext<Unit>(ioDispatcher) {
        bookDao.deleteBookById(bookId)
    }

    override fun loadAllQuotes(): LiveData<Result<List<QuoteEntity>>> {
        return quoteDao.loadAllQuotes().map {
            Success(it)
        }
    }

    override fun loadQuotesByBook(bookId: String): LiveData<Result<List<QuoteEntity>>> {
        return quoteDao.loadQuotesByBook(bookId).map {
            Success(it)
        }
    }

    override fun loadQuotesByCategory(categoryId: String): LiveData<Result<List<QuoteEntity>>> {
        return quoteDao.loadQuotesByCategory(categoryId).map {
            Success(it)
        }
    }

    override suspend fun saveQuote(quote: QuoteEntity) = withContext(ioDispatcher) {
        quoteDao.saveQuote(quote)
    }

    override suspend fun updateQuote(quote: QuoteEntity) = withContext<Unit>(ioDispatcher) {
        quoteDao.updateQuote(quote)
    }

    override suspend fun deleteQuoteById(quoteId: String) = withContext<Unit>(ioDispatcher) {
        quoteDao.deleteQuoteById(quoteId)
    }

    override fun loadCategoriesByType(type: Int): LiveData<Result<List<CategoryEntity>>> {
        return categoryDao.loadAllCategories(type).map {
            Success(it)
        }
    }

    override suspend fun saveCategory(category: CategoryEntity) = withContext(ioDispatcher) {
        categoryDao.saveCategory(category)
    }

    override suspend fun updateCategory(category: CategoryEntity) =
        withContext<Unit>(ioDispatcher) {
            categoryDao.updateCategory(category)
        }

    override suspend fun deleteCategoryById(categoryId: String) = withContext<Unit>(ioDispatcher) {
        categoryDao.deleteCategoryById(categoryId)
    }
}