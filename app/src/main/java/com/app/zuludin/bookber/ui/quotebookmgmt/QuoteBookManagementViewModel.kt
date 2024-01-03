package com.app.zuludin.bookber.ui.quotebookmgmt

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
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
    val isLoading: Boolean = false,
    val isError: Boolean = false
)

@HiltViewModel
class QuoteBookManagementViewModel @Inject constructor(private val repository: BookberRepository) :
    ViewModel() {

    val bookId = MutableLiveData<String>()

    val bookCategories = MutableLiveData<List<CategoryEntity>>()
    val bookQuotes = MutableLiveData<MutableList<Quote>>()

    val bookTitle = MutableLiveData<String>()
    val bookAuthor = MutableLiveData<String>()
    val bookCategory = MutableLiveData<CategoryEntity?>()
    val bookImage = MutableLiveData<String>()

    val quoteCache = mutableListOf<Quote>()

    private val _uiState = MutableStateFlow(QuoteBookManagementUiState())
    val uiState: StateFlow<QuoteBookManagementUiState> = _uiState

    fun start(bookId: String) {
        this.bookId.value = bookId

        viewModelScope.launch {
            repository.loadBookDetail(bookId).let { result ->
                if (result is Result.Success) {
                    quoteCache.addAll(result.data.quotes)
                    bookQuotes.value = result.data.quotes.toMutableList()
                    _uiState.update {
                        it.copy(
                            book = result.data.book,
                            quotes = result.data.quotes.toMutableList(),
                            category = result.data.category,
                            isLoading = false,
                            isError = false
                        )
                    }
//                    bookTitle.value = result.data.bookEntity.title
//                    bookAuthor.value = result.data.bookEntity.author
//                    bookCategory.value = result.data.category
//                    bookImage.value = result.data.bookEntity.cover
//                    bookQuotes.value = result.data.quotes.toMutableList()
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
                    bookCategories.value = convertBookCategoryList(emptyList())
                }
            }
        }
    }

    fun saveBook(book: Book, quotes: List<QuoteEntity>) {
//        val currentTitle = bookTitle.value
//        val currentAuthor = bookAuthor.value
//        val currentImage = bookImage.value
//        val currentCategory = bookCategory.value
//        if (currentTitle == null || currentAuthor == null || currentImage == null || currentCategory == null) {
//            return
//        }

        val bookId = bookId.value

//        val book = BookEntity(
//            title = currentTitle,
//            author = currentAuthor,
//            cover = currentImage,
//            categoryId = currentCategory.id
//        )

        if (bookId == null) {
            viewModelScope.launch {
                repository.saveBook(book)
            }
        } else {
            book.bookId = bookId
            viewModelScope.launch {
                repository.updateBook(book)
            }
        }

//        if (bookId == null) {
//            updateQuotesBookId(quotes, book.id)
//            this.bookId.value = book.id
//            viewModelScope.launch {
//                repository.saveBook(book)
//            }
//        } else {
//            book.id = bookId
//            updateBook(book)
//        }
    }

    fun updateBook(book: BookEntity) {
        viewModelScope.launch {
//            repository.updateBook(book)
        }
    }

    fun saveQuote(quote: Quote) {
//        quote.bookId = bookId.value ?: ""

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

    private fun convertQuoteCategoryList(list: List<CategoryEntity>) = list

    private fun convertBookCategoryList(list: List<CategoryEntity>) = list

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