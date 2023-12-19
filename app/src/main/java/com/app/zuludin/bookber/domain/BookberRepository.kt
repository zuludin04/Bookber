package com.app.zuludin.bookber.domain

import androidx.lifecycle.LiveData
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.data.local.entity.relations.BookDetailEntity
import com.app.zuludin.bookber.data.local.entity.relations.QuoteDetailEntity

interface BookberRepository {
    fun loadBookStore(): LiveData<Result<List<BookEntity>>>

    suspend fun loadBooks(): Result<List<BookEntity>>

    fun loadBooksByCategory(categoryId: String): LiveData<Result<List<BookEntity>>>

    suspend fun loadBookDetail(bookId: String): Result<BookDetailEntity>

    suspend fun saveBook(bookEntity: BookEntity)

    suspend fun updateBook(bookEntity: BookEntity)

    suspend fun deleteBookById(bookId: String)

    fun loadAllQuotes(): LiveData<Result<List<QuoteEntity>>>

    fun loadQuotesByBook(bookId: String): LiveData<Result<List<QuoteEntity>>>

    fun loadQuotesByCategory(categoryId: String): LiveData<Result<List<QuoteEntity>>>

    suspend fun loadQuoteDetail(quoteId: String): Result<QuoteDetailEntity>

    suspend fun insertQuotesIntoBooks(quotes: List<QuoteEntity>)

    suspend fun saveQuote(quote: QuoteEntity)

    suspend fun updateQuote(quote: QuoteEntity)

    suspend fun deleteQuoteById(quoteId: String)

    fun loadCategoriesByType(type: Int): LiveData<Result<List<CategoryEntity>>>

    suspend fun loadCategories(type: Int): Result<List<CategoryEntity>>

    suspend fun saveCategory(category: CategoryEntity)

    suspend fun deleteCategoryById(categoryId: String)
}