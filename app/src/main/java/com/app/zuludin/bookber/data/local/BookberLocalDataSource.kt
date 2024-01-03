package com.app.zuludin.bookber.data.local

import androidx.lifecycle.LiveData
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.data.local.entity.relations.BookDetailEntity
import com.app.zuludin.bookber.data.local.entity.relations.BookWithQuoteTotal
import com.app.zuludin.bookber.data.local.entity.relations.QuoteDetailEntity
import kotlinx.coroutines.flow.Flow

interface BookberLocalDataSource {
    fun observeAllBooks(): Flow<List<BookWithQuoteTotal>>

    fun loadBookWithQuotes(): LiveData<Result<List<BookWithQuoteTotal>>>

    suspend fun loadBooks(): List<BookWithQuoteTotal>

    suspend fun loadBooksByCategory(categoryId: String): Result<List<BookWithQuoteTotal>>

    suspend fun loadBookDetail(bookId: String): Result<BookDetailEntity>

    suspend fun saveBook(book: BookEntity)

    suspend fun updateBook(book: BookEntity)

    suspend fun deleteBookById(bookId: String)

    fun observeAllQuotes(): Flow<List<QuoteEntity>>

    suspend fun insertQuotesIntoBooks(quotes: List<QuoteEntity>)

    fun loadQuotesByBook(bookId: String): LiveData<Result<List<QuoteEntity>>>

    suspend fun loadQuotesByCategory(categoryId: String): Result<List<QuoteEntity>>

    suspend fun loadCategories(type: Int): List<CategoryEntity>

    suspend fun loadQuoteDetail(quoteId: String): QuoteDetailEntity?

    suspend fun saveQuote(quote: QuoteEntity)

    suspend fun updateQuote(quote: QuoteEntity)

    suspend fun deleteQuoteById(quoteId: String)

    fun observeCategoryByType(type: Int): Flow<List<CategoryEntity>>

    suspend fun saveCategory(category: CategoryEntity)

    suspend fun deleteCategoryById(categoryId: String)
}