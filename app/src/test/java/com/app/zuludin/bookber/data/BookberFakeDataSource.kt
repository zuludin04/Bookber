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

class BookberFakeDataSource(
    private var books: MutableList<BookEntity>? = mutableListOf(),
    private var booksWithQuote: MutableList<BookWithQuoteTotal>? = mutableListOf(),
    private var quotes: MutableList<QuoteEntity>? = mutableListOf(),
    private var categories: MutableList<CategoryEntity>? = mutableListOf()
) :
    BookberLocalDataSource {
    override fun loadBookStore(): LiveData<Result<List<BookWithQuoteTotal>>> {
        val observableBooks = MutableLiveData<Result<List<BookWithQuoteTotal>>>()
        observableBooks.value = Result.Success(booksWithQuote ?: arrayListOf())
        return observableBooks
    }

    override fun loadBookWithQuotes(): LiveData<Result<List<BookWithQuoteTotal>>> {
        return MutableLiveData()
    }

    override suspend fun loadBooks(): Result<List<BookWithQuoteTotal>> {
        return Result.Success(emptyList())
    }

    override suspend fun loadBooksByCategory(categoryId: String): Result<List<BookWithQuoteTotal>> {
        val observableBooks = MutableLiveData<Result<List<BookWithQuoteTotal>>>()
        return observableBooks.value!!
    }

    override suspend fun loadBookDetail(bookId: String): Result<BookDetailEntity> {
        //        books?.firstOrNull { it.id == bookId }?.let { observableBook.value = Result.Success(it) }
        return Result.Success(BookDetailEntity(BookEntity(), emptyList(), CategoryEntity()))
    }

    override suspend fun saveBook(book: BookEntity) {
        books?.add(book)
    }

    override suspend fun updateBook(book: BookEntity) {
        books?.firstOrNull { it.id == book.id }?.let { it.title = book.title }
    }

    override suspend fun deleteBookById(bookId: String) {
        books?.removeIf { it.id == bookId }
    }

    override fun observeAllQuotes(): LiveData<Result<List<QuoteEntity>>> {
        val observableQuotes = MutableLiveData<Result<List<QuoteEntity>>>()
        quotes?.let { observableQuotes.value = Result.Success(it) }
        return observableQuotes
    }

    override suspend fun insertQuotesIntoBooks(quotes: List<QuoteEntity>) {

    }

    override fun loadQuotesByBook(bookId: String): LiveData<Result<List<QuoteEntity>>> {
        val observableQuotes = MutableLiveData<Result<List<QuoteEntity>>>()
        quotes?.filter { it.bookId == bookId }?.let { observableQuotes.value = Result.Success(it) }
        return observableQuotes
    }

    override suspend fun loadQuotesByCategory(categoryId: String): Result<List<QuoteEntity>> {
        val observableQuotes = MutableLiveData<Result<List<QuoteEntity>>>()
        quotes?.filter { it.categoryId == categoryId }
            ?.let { observableQuotes.value = Result.Success(it) }
        return observableQuotes.value!!
    }

    override suspend fun loadCategories(type: Int): Result<List<CategoryEntity>> {
        return Result.Success(emptyList())
    }

    override suspend fun loadQuoteDetail(quoteId: String): Result<QuoteDetailEntity> {
        return Result.Success(QuoteDetailEntity(QuoteEntity(), BookEntity(), CategoryEntity()))
    }

    override suspend fun saveQuote(quote: QuoteEntity) {
        quotes?.add(quote)
    }

    override suspend fun updateQuote(quote: QuoteEntity) {
        quotes?.firstOrNull { it.id == quote.id }?.let { it.quotes = quote.quotes }
    }

    override suspend fun deleteQuoteById(quoteId: String) {
        quotes?.removeIf { it.id == quoteId }
    }

    override fun loadCategoriesByType(type: Int): LiveData<Result<List<CategoryEntity>>> {
        val observableCategories = MutableLiveData<Result<List<CategoryEntity>>>()
        categories?.filter { it.type == type }
            ?.let { observableCategories.value = Result.Success(it) }
        return observableCategories
    }

    override suspend fun saveCategory(category: CategoryEntity) {
        categories?.add(category)
    }

    override suspend fun deleteCategoryById(categoryId: String) {
        categories?.removeIf { it.id == categoryId }
    }
}