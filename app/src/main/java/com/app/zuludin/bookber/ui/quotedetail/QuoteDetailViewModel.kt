package com.app.zuludin.bookber.ui.quotedetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.domain.BookberRepository
import kotlinx.coroutines.launch

class QuoteDetailViewModel(private val repository: BookberRepository) : ViewModel() {
    private val _quoteId = MutableLiveData<String>()

    val quoteDetail = MutableLiveData<QuoteEntity>()
    val quoteCategory = MutableLiveData<CategoryEntity>()
    val quoteBookInfo = MutableLiveData<BookEntity?>()
    val bookImage = MutableLiveData<String>()

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
    }

    suspend fun deleteQuote() {
        _quoteId.value?.let {
            repository.deleteQuoteById(it)
        }
    }

    fun updateQuote(quote: String, author: String) {
        val old = quoteDetail.value!!
        old.quotes = quote
        old.author = author
        quoteDetail.value = old
    }
}