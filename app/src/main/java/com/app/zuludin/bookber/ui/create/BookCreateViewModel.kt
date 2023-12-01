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

    private val _quoteCategories: LiveData<List<CategoryEntity>> =
        repository.loadCategoriesByType(1).distinctUntilChanged()
            .switchMap { observeQuoteCategories(it) }
    val quoteCategories: LiveData<List<CategoryEntity>> = _quoteCategories

    private val _bookCategories: LiveData<List<CategoryEntity>> =
        repository.loadCategoriesByType(2).distinctUntilChanged()
            .switchMap { observeBookCategory(it) }
    val bookCategories: LiveData<List<CategoryEntity>> = _bookCategories

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

    fun getQuotes() = repository.loadAllQuotes()

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
        viewModelScope.launch {
            repository.saveQuote(quote)
        }
    }
}