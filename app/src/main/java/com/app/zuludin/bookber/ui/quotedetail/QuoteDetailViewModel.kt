package com.app.zuludin.bookber.ui.quotedetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.data.local.entity.relations.BookWithQuoteTotal
import com.app.zuludin.bookber.domain.BookberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteDetailViewModel @Inject constructor(private val repository: BookberRepository) :
    ViewModel() {
    private val _quoteId = MutableLiveData<String>()

    val quoteDetail = MutableLiveData<QuoteEntity>()
    val quoteCategory = MutableLiveData<CategoryEntity?>()
    val quoteBookInfo = MutableLiveData<BookEntity?>()
    val bookImage = MutableLiveData<String>()

    val categories = MutableLiveData<List<CategoryEntity>>()
    val books = MutableLiveData<List<BookWithQuoteTotal>>()

    fun start(quoteId: String) {
        _quoteId.value = quoteId

        viewModelScope.launch {
            repository.loadQuoteDetail(quoteId).let { result ->
                if (result is Result.Success) {
                    quoteDetail.value = result.data.quoteEntity
                    quoteCategory.value = result.data.categoryEntity
                    quoteBookInfo.value = result.data.bookEntity
                    bookImage.value = result.data.bookEntity?.cover
                }
            }
        }

        viewModelScope.launch {
            repository.loadCategories(1).let { result ->
                if (result is Result.Success) {
                    categories.value = loadedCategories(result.data)
                }
            }
        }

        viewModelScope.launch {
            repository.loadBooks().let { result ->
                if (result is Result.Success) {
                    books.value = loadedBooks(result.data)
                }
            }
        }
    }

    private fun loadedCategories(input: List<CategoryEntity>): List<CategoryEntity> {
        val cats = ArrayList<CategoryEntity>()
        cats.addAll(input)
        return cats
    }

    private fun loadedBooks(input: List<BookWithQuoteTotal>): List<BookWithQuoteTotal> {
        val books = ArrayList<BookWithQuoteTotal>()
        books.addAll(input)
        return books
    }

    suspend fun deleteQuote() {
        _quoteId.value?.let {
            repository.deleteQuoteById(it)
        }
    }

    fun updateQuote(quote: String, author: String, category: CategoryEntity) {
        val old = quoteDetail.value!!
        old.quotes = quote
        old.author = author
        old.categoryId = category.id
        quoteDetail.value = old

        viewModelScope.launch {
            repository.updateQuote(old)
            quoteCategory.value = category
        }
    }

    fun addBookInfo(book: BookEntity) {
        quoteBookInfo.value = book
        bookImage.value = book.cover

        viewModelScope.launch {
            val old = quoteDetail.value!!
            old.bookId = book.id
            repository.updateQuote(old)
        }
    }

    fun removeBookInfo() {
        quoteBookInfo.value = null
        bookImage.value = ""

        viewModelScope.launch {
            val old = quoteDetail.value!!
            old.bookId = ""
            repository.updateQuote(old)
        }
    }
}