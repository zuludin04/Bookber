package com.app.zuludin.bookber.domain

import androidx.lifecycle.LiveData
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.domain.model.Book
import com.app.zuludin.bookber.domain.model.BookDetail
import com.app.zuludin.bookber.domain.model.Category
import com.app.zuludin.bookber.domain.model.Quote
import com.app.zuludin.bookber.domain.model.QuoteDetail
import kotlinx.coroutines.flow.Flow

interface BookberRepository {
    fun observeAllBooks(): Flow<Result<List<Book>>>

    suspend fun loadBooks(): Result<List<Book>>

    suspend fun loadBookDetail(bookId: String): Result<BookDetail>

    suspend fun saveBook(book: Book)

    suspend fun updateBook(book: Book)

    suspend fun deleteBookById(bookId: String)

    fun observeAllQuotes(): Flow<Result<List<Quote>>>

    fun loadQuotesByBook(bookId: String): LiveData<Result<List<QuoteEntity>>>

    suspend fun loadQuoteDetail(quoteId: String): Result<QuoteDetail>

    suspend fun insertQuotesIntoBooks(quotes: List<QuoteEntity>)

    suspend fun saveQuote(quote: Quote)

    suspend fun updateQuote(quote: Quote)

    suspend fun deleteQuoteById(quoteId: String)

    fun observeCategoryByType(type: Int): Flow<Result<List<Category>>>

    suspend fun loadCategories(type: Int): Result<List<Category>>

    suspend fun saveCategory(category: CategoryEntity)

    suspend fun deleteCategoryById(categoryId: String)
}