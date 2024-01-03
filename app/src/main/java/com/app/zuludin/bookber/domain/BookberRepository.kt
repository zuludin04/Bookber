package com.app.zuludin.bookber.domain

import androidx.lifecycle.LiveData
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.data.local.entity.relations.BookDetailEntity
import com.app.zuludin.bookber.data.local.entity.relations.BookWithQuoteTotal
import com.app.zuludin.bookber.domain.model.Book
import com.app.zuludin.bookber.domain.model.Category
import com.app.zuludin.bookber.domain.model.Quote
import com.app.zuludin.bookber.domain.model.QuoteDetail
import kotlinx.coroutines.flow.Flow

interface BookberRepository {
    fun observeAllBooks(): Flow<Result<List<Book>>>

    fun loadBookWithQuotes(): LiveData<Result<List<BookWithQuoteTotal>>>

    suspend fun loadBooks(): Result<List<Book>>

    suspend fun loadBooksByCategory(categoryId: String): Result<List<BookWithQuoteTotal>>

    suspend fun loadBookDetail(bookId: String): Result<BookDetailEntity>

    suspend fun saveBook(bookEntity: BookEntity)

    suspend fun updateBook(bookEntity: BookEntity)

    suspend fun deleteBookById(bookId: String)

    fun observeAllQuotes(): Flow<Result<List<Quote>>>

    fun loadQuotesByBook(bookId: String): LiveData<Result<List<QuoteEntity>>>

    suspend fun loadQuotesByCategory(categoryId: String): Result<List<QuoteEntity>>

    suspend fun loadQuoteDetail(quoteId: String): Result<QuoteDetail>

    suspend fun insertQuotesIntoBooks(quotes: List<QuoteEntity>)

    suspend fun saveQuote(quote: QuoteEntity)

    suspend fun updateQuote(quote: Quote)

    suspend fun deleteQuoteById(quoteId: String)

    fun observeCategoryByType(type: Int): Flow<Result<List<Category>>>

    suspend fun loadCategories(type: Int): Result<List<Category>>

    suspend fun saveCategory(category: CategoryEntity)

    suspend fun deleteCategoryById(categoryId: String)
}