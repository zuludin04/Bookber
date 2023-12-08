package com.app.zuludin.bookber.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.domain.BookberRepository
import kotlinx.coroutines.launch

class BookCreateViewModel(private val repository: BookberRepository) : ViewModel() {

    private val _bookId = MutableLiveData<String>()

    private val _quoteCategories: LiveData<List<CategoryEntity>> =
        repository.loadCategoriesByType(1).distinctUntilChanged()
            .switchMap { observeQuoteCategories(it) }
    val quoteCategories: LiveData<List<CategoryEntity>> = _quoteCategories

    private val _bookCategories: LiveData<List<CategoryEntity>> =
        repository.loadCategoriesByType(2).distinctUntilChanged()
            .switchMap { observeBookCategory(it) }
    val bookCategories: LiveData<List<CategoryEntity>> = _bookCategories

    private val cacheBookQuotes = ArrayList<QuoteEntity>()
    val bookQuotes = MutableLiveData<MutableList<QuoteEntity>>()

    val bookTitle = MutableLiveData<String>()
    val bookAuthor = MutableLiveData<String>()
    val bookCategory = MutableLiveData<CategoryEntity>()
    val bookImage = MutableLiveData<String>()

    fun getCurrentQuotes(bookId: String) = repository.loadQuotesByBook(bookId)

    private fun observeQuoteCategories(categoryResult: Result<List<CategoryEntity>>): LiveData<List<CategoryEntity>> {
        val result = MutableLiveData<List<CategoryEntity>>()

        if (categoryResult is Result.Success) {
            viewModelScope.launch {
                result.value = convertQuoteCategoryList(categoryResult.data)
            }
        } else {
            result.value = emptyList()
        }

        return result
    }

    private fun convertQuoteCategoryList(list: List<CategoryEntity>) = list

    private fun observeBookCategory(categoryResult: Result<List<CategoryEntity>>): LiveData<List<CategoryEntity>> {
        val result = MutableLiveData<List<CategoryEntity>>()

        if (categoryResult is Result.Success) {
            viewModelScope.launch {
                result.value = convertBookCategoryList(categoryResult.data)
            }
        } else {
            result.value = emptyList()
        }

        return result
    }

    private fun convertBookCategoryList(list: List<CategoryEntity>) = list

    fun start(bookId: String) {
        _bookId.value = bookId

        viewModelScope.launch {
            repository.loadBookDetail(bookId).let { result ->
                if (result is Result.Success) {
                    bookTitle.value = result.data.bookEntity.title
                    bookAuthor.value = result.data.bookEntity.author
                    bookCategory.value = result.data.category
                    bookImage.value = result.data.bookEntity.cover
                    bookQuotes.value = result.data.quotes.toMutableList()
                    cacheBookQuotes.addAll(result.data.quotes)
                }
            }
        }
    }

    fun saveBook(book: BookEntity) {
        viewModelScope.launch {
            repository.saveBook(book)
        }
    }

    fun updateBook(book: BookEntity) {
        viewModelScope.launch {
            repository.updateBook(book)
        }
    }

    fun saveQuote(quote: QuoteEntity) {
        updateBookQuotes(quote)
        viewModelScope.launch {
            repository.saveQuote(quote)
        }
    }

    fun deleteQuote(quoteId: String) {
        viewModelScope.launch {
            repository.deleteQuoteById(quoteId)
        }
    }

    fun removeFromBook(quote: QuoteEntity) {
        quote.bookId = ""
        viewModelScope.launch {
            repository.updateQuote(quote)
        }
    }

    private fun updateBookQuotes(quote: QuoteEntity) {
        cacheBookQuotes.add(quote.copy())
        bookQuotes.value = cacheBookQuotes
    }
}