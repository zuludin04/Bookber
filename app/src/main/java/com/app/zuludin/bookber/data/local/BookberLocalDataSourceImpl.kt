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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookberLocalDataSourceImpl internal constructor(
    private val bookDao: BookDao,
    private val quoteDao: QuoteDao,
    private val categoryDao: CategoryDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BookberLocalDataSource {
    override fun loadBookStore(): LiveData<Result<List<BookWithQuoteTotal>>> {
        return bookDao.loadBookStore().map {
            Success(it)
        }
    }

    override fun loadBookWithQuotes(): LiveData<Result<List<BookWithQuoteTotal>>> {
        return bookDao.loadBookWithQuoteTotal().map {
            Success(it)
        }
    }

    override suspend fun loadBooks(): Result<List<BookWithQuoteTotal>> = withContext(ioDispatcher) {
        try {
            val books = bookDao.loadBooks()
            if (books.isNotEmpty()) {
                return@withContext Success(books)
            } else {
                return@withContext Success(emptyList())
            }
        } catch (e: Exception) {
            return@withContext Error(e)
        }
    }

    override suspend fun loadBooksByCategory(categoryId: String): Result<List<BookWithQuoteTotal>> =
        withContext(ioDispatcher) {
            try {
                val book = bookDao.loadBooksByCategory(categoryId)
                if (book.isNotEmpty()) {
                    return@withContext Success(book)
                } else {
                    return@withContext Success(emptyList())
                }
            } catch (e: Exception) {
                return@withContext Error(e)
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

    override suspend fun insertQuotesIntoBooks(quotes: List<QuoteEntity>) =
        withContext(ioDispatcher) {
            quoteDao.batchUpdate(quotes)
        }

    override fun loadQuotesByBook(bookId: String): LiveData<Result<List<QuoteEntity>>> {
        return quoteDao.loadQuotesByBook(bookId).map {
            Success(it)
        }
    }

    override suspend fun loadQuotesByCategory(categoryId: String): Result<List<QuoteEntity>> =
        withContext(ioDispatcher) {
            try {
                val quotes = quoteDao.loadQuotesByCategory(categoryId)
                if (quotes.isNotEmpty()) {
                    return@withContext Success(quotes)
                } else {
                    return@withContext Success(emptyList())
                }
            } catch (e: Exception) {
                return@withContext Error(e)
            }
        }

    override suspend fun loadCategories(type: Int): Result<List<CategoryEntity>> =
        withContext(ioDispatcher) {
            try {
                val categories = categoryDao.loadCategories(type)
                if (categories.isNotEmpty()) {
                    return@withContext Success(categories)
                } else {
                    return@withContext Success(emptyList())
                }
            } catch (e: Exception) {
                return@withContext Error(e)
            }
        }

    override suspend fun loadQuoteDetail(quoteId: String): Result<QuoteDetailEntity> =
        withContext(ioDispatcher) {
            try {
                val book = quoteDao.loadQuoteDetail(quoteId)
                if (book != null) {
                    return@withContext Success(book)
                } else {
                    return@withContext Error(Exception(""))
                }
            } catch (e: Exception) {
                return@withContext Error(e)
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

    override suspend fun deleteCategoryById(categoryId: String) = withContext<Unit>(ioDispatcher) {
        categoryDao.deleteCategoryById(categoryId)
    }
}