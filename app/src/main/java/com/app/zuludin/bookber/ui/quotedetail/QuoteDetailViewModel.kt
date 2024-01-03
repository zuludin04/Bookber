package com.app.zuludin.bookber.ui.quotedetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.domain.BookberRepository
import com.app.zuludin.bookber.domain.model.Book
import com.app.zuludin.bookber.domain.model.Category
import com.app.zuludin.bookber.domain.model.Quote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class QuoteDetailScreenUiState(
    val quote: Quote = Quote(),
    val category: Category? = null,
    val book: Book? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)

@HiltViewModel
class QuoteDetailViewModel @Inject constructor(private val repository: BookberRepository) :
    ViewModel() {
    private val _quoteId = MutableLiveData<String>()

    val categories = MutableLiveData<List<Category>>()
    val books = MutableLiveData<List<Book>>()

    private val _uiState = MutableStateFlow(QuoteDetailScreenUiState())
    val uiState: StateFlow<QuoteDetailScreenUiState> = _uiState

    fun start(quoteId: String) {
        _quoteId.value = quoteId

        viewModelScope.launch {
            val detailResult = async { repository.loadQuoteDetail(quoteId) }.await()
            val categoriesResult = async { repository.loadCategories(1) }.await()
            val booksResult = async { repository.loadBooks() }.await()

            if (detailResult is Result.Success) {
                _uiState.update {
                    it.copy(
                        quote = detailResult.data.quote,
                        book = detailResult.data.book,
                        category = detailResult.data.category
                    )
                }
            } else {
                _uiState.update { it.copy(isError = true) }
            }

            if (categoriesResult is Result.Success) {
                categories.value = loadedCategories(categoriesResult.data)
            }

            if (booksResult is Result.Success) {
                books.value = loadedBooks(booksResult.data)
            }
        }
    }

    private fun loadedCategories(input: List<Category>): List<Category> {
        val cats = ArrayList<Category>()
        cats.addAll(input)
        return cats
    }

    private fun loadedBooks(input: List<Book>): List<Book> {
        val books = ArrayList<Book>()
        books.addAll(input)
        return books
    }

    suspend fun deleteQuote() {
        _quoteId.value?.let {
            repository.deleteQuoteById(it)
        }
    }

    fun updateQuote(quote: String, author: String, category: Category) {
        var updatedQuote = Quote()
        _uiState.update {
            val new = it.quote.copy(quote = quote, author = author, categoryId = category.id)
            updatedQuote = new
            it.copy(quote = new, category = category)
        }

        viewModelScope.launch {
            repository.updateQuote(updatedQuote)
        }
    }

    fun addBookInfo(book: Book) {
        var quote = Quote()
        _uiState.update {
            quote = it.quote.copy(bookId = book.bookId)
            it.copy(book = book)
        }
        viewModelScope.launch {
            repository.updateQuote(quote)
        }
    }

    fun removeBookInfo() {
        var quote = Quote()
        _uiState.update {
            quote = it.quote.copy(bookId = "")
            it.copy(book = null)
        }
        viewModelScope.launch {
            repository.updateQuote(quote)
        }
    }
}