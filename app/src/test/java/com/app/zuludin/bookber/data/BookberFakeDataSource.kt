package com.app.zuludin.bookber.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.zuludin.bookber.data.local.BookberLocalDataSource
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.data.local.entity.relations.BookDetailEntity
import com.app.zuludin.bookber.data.local.entity.relations.BookWithQuoteTotal
import com.app.zuludin.bookber.data.local.entity.relations.QuoteDetailEntity
import com.app.zuludin.bookber.domain.model.Quote
import kotlinx.coroutines.flow.Flow

class BookberFakeDataSource(
    private var books: MutableList<BookEntity>? = mutableListOf(),
    private var booksWithQuote: MutableList<BookWithQuoteTotal>? = mutableListOf(),
    private var quotes: MutableList<QuoteEntity>? = mutableListOf(),
    private var categories: MutableList<CategoryEntity>? = mutableListOf()
) {
     fun observeAllBooks(): Flow<List<BookWithQuoteTotal>> {
        TODO("Not yet implemented")
    }

     fun loadBookWithQuotes(): LiveData<Result<List<BookWithQuoteTotal>>> {
        return MutableLiveData()
    }

     suspend fun loadBooks(): Result<List<BookWithQuoteTotal>> {
        return Result.Success(emptyList())
    }

     suspend fun loadBooksByCategory(categoryId: String): Result<List<BookWithQuoteTotal>> {
        val observableBooks = MutableLiveData<Result<List<BookWithQuoteTotal>>>()
        return observableBooks.value!!
    }

     suspend fun loadBookDetail(bookId: String): Result<BookDetailEntity> {
        //        books?.firstOrNull { it.id == bookId }?.let { observableBook.value = Result.Success(it) }
        return Result.Success(BookDetailEntity(BookEntity(), emptyList(), CategoryEntity()))
    }

     suspend fun saveBook(book: BookEntity) {
        books?.add(book)
    }

     suspend fun updateBook(book: BookEntity) {
        books?.firstOrNull { it.id == book.id }?.let { it.title = book.title }
    }

     suspend fun deleteBookById(bookId: String) {
        books?.removeIf { it.id == bookId }
    }

     fun observeAllQuotes(): Flow<List<QuoteEntity>> {
        TODO("Not yet implemented")
    }

     suspend fun insertQuotesIntoBooks(quotes: List<QuoteEntity>) {

    }

     fun loadQuotesByBook(bookId: String): LiveData<Result<List<QuoteEntity>>> {
        val observableQuotes = MutableLiveData<Result<List<QuoteEntity>>>()
        quotes?.filter { it.bookId == bookId }?.let { observableQuotes.value = Result.Success(it) }
        return observableQuotes
    }

     suspend fun loadQuotesByCategory(categoryId: String): Result<List<QuoteEntity>> {
        val observableQuotes = MutableLiveData<Result<List<QuoteEntity>>>()
        quotes?.filter { it.categoryId == categoryId }
            ?.let { observableQuotes.value = Result.Success(it) }
        return observableQuotes.value!!
    }

     suspend fun loadCategories(type: Int): Result<List<CategoryEntity>> {
        return Result.Success(emptyList())
    }

     suspend fun loadQuoteDetail(quoteId: String): Result<QuoteDetailEntity> {
        return Result.Success(QuoteDetailEntity(QuoteEntity(), BookEntity(), CategoryEntity()))
    }

     suspend fun saveQuote(quote: QuoteEntity) {
        quotes?.add(quote)
    }

     suspend fun updateQuote(quote: QuoteEntity) {
        quotes?.firstOrNull { it.id == quote.id }?.let { it.quotes = quote.quotes }
    }

     suspend fun deleteQuoteById(quoteId: String) {
        quotes?.removeIf { it.id == quoteId }
    }

     fun observeCategoryByType(type: Int): Flow<List<CategoryEntity>> {
        TODO("Not yet implemented")
    }

     suspend fun saveCategory(category: CategoryEntity) {
        categories?.add(category)
    }

     suspend fun deleteCategoryById(categoryId: String) {
        categories?.removeIf { it.id == categoryId }
    }
}