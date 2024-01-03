package com.app.zuludin.bookber.ui.quotebookmgmt

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.domain.BookberRepository
import com.app.zuludin.bookber.domain.model.Book
import com.app.zuludin.bookber.domain.model.Category
import com.app.zuludin.bookber.domain.model.Quote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList
import javax.inject.Inject

data class QuoteBookManagementUiState(
    val book: Book? = null,
    var quotes: List<Quote> = emptyList(),
    val category: Category? = null,
    val quoteCategories: List<Category> = emptyList(),
    val bookCategories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)

@HiltViewModel
class QuoteBookManagementViewModel @Inject constructor(private val repository: BookberRepository) :
    ViewModel() {

    val bookId = MutableLiveData<String>()

    private val quoteCache = mutableListOf<Quote>()

    private val _uiState = MutableStateFlow(QuoteBookManagementUiState())
    val uiState: StateFlow<QuoteBookManagementUiState> = _uiState

    fun start(bookId: String) {
        this.bookId.value = bookId

        viewModelScope.launch {
            repository.loadBookDetail(bookId).let { result ->
                if (result is Result.Success) {
                    quoteCache.addAll(result.data.quotes)
                    _uiState.update {
                        it.copy(
                            book = result.data.book,
                            quotes = result.data.quotes.toMutableList(),
                            category = result.data.category,
                            isLoading = false,
                            isError = false
                        )
                    }
                }
            }
        }
    }

    fun populateCategories() {
        viewModelScope.launch {
            repository.loadCategories(1).let { result ->
                if (result is Result.Success) {
                    _uiState.update { it.copy(quoteCategories = result.data) }
                }
            }
        }

        viewModelScope.launch {
            repository.loadCategories(2).let { result ->
                if (result is Result.Success) {
                    _uiState.update { it.copy(bookCategories = result.data) }
                }
            }
        }
    }

    fun saveBook(book: Book, quotes: List<QuoteEntity>) {
        val bookId = bookId.value

        if (bookId == null) {
//            updateQuotesBookId(quotes, book.id)
//            this.bookId.value = book.id
            viewModelScope.launch {
                repository.saveBook(book)
            }
        } else {
            book.bookId = bookId
            viewModelScope.launch {
                repository.updateBook(book)
            }
        }
    }

    fun saveQuote(quote: Quote) {
        quoteCache.add(quote)
        _uiState.update {
            it.copy(quotes = quoteCache.toImmutableList())
        }

        viewModelScope.launch {
            repository.saveQuote(quote)
        }
    }

    suspend fun deleteBook() {
        bookId.value?.let {
            repository.deleteBookById(it)
        }
    }

    private fun updateQuotesBookId(quotes: List<QuoteEntity>, bookId: String) {
        val result = ArrayList<QuoteEntity>()
        result.addAll(quotes)

        result.forEach {
            it.bookId = bookId
        }

        viewModelScope.launch {
            repository.insertQuotesIntoBooks(result)
        }
    }
}