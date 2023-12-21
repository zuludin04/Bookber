package com.app.zuludin.bookber.ui.quotebookmgmt

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.domain.BookberRepository
import kotlinx.coroutines.launch

class QuoteBookManagementViewModel(private val repository: BookberRepository) : ViewModel() {

    val bookId = MutableLiveData<String>()

    val quoteCategories = MutableLiveData<List<CategoryEntity>>()
    val bookCategories = MutableLiveData<List<CategoryEntity>>()
    val bookQuotes = MutableLiveData<MutableList<QuoteEntity>>()

    val bookTitle = MutableLiveData<String>()
    val bookAuthor = MutableLiveData<String>()
    val bookCategory = MutableLiveData<CategoryEntity?>()
    val bookImage = MutableLiveData<String>()

    fun start(bookId: String) {
        this.bookId.value = bookId

        viewModelScope.launch {
            repository.loadBookDetail(bookId).let { result ->
                if (result is Result.Success) {
                    bookTitle.value = result.data.bookEntity.title
                    bookAuthor.value = result.data.bookEntity.author
                    bookCategory.value = result.data.category
                    bookImage.value = result.data.bookEntity.cover
                    bookQuotes.value = result.data.quotes.toMutableList()
                }
            }
        }
    }

    fun populateCategories() {
        viewModelScope.launch {
            repository.loadCategories(1).let { result ->
                if (result is Result.Success) {
                    quoteCategories.value = convertQuoteCategoryList(result.data)
                }
            }
        }

        viewModelScope.launch {
            repository.loadCategories(2).let { result ->
                if (result is Result.Success) {
                    bookCategories.value = convertBookCategoryList(result.data)
                }
            }
        }
    }

    fun saveBook(quotes: List<QuoteEntity>) {
        val currentTitle = bookTitle.value
        val currentAuthor = bookAuthor.value
        val currentImage = bookImage.value
        val currentCategory = bookCategory.value
        if (currentTitle == null || currentAuthor == null || currentImage == null || currentCategory == null) {
            return
        }

        val bookId = bookId.value

        val book = BookEntity(
            title = currentTitle,
            author = currentAuthor,
            cover = currentImage,
            categoryId = currentCategory.id
        )

        if (bookId == null) {
            updateQuotesBookId(quotes, book.id)
            this.bookId.value = book.id
            viewModelScope.launch {
                repository.saveBook(book)
            }
        } else {
            book.id = bookId
            updateBook(book)
        }
    }

    fun updateBook(book: BookEntity) {
        viewModelScope.launch {
            repository.updateBook(book)
        }
    }

    fun saveQuote(quote: QuoteEntity) {
        quote.bookId = bookId.value ?: ""
        viewModelScope.launch {
            repository.saveQuote(quote)
            if (bookId.value != null) {
                start(bookId.value!!)
            }
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