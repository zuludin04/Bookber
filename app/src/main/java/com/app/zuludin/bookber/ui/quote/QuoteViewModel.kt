package com.app.zuludin.bookber.ui.quote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.domain.BookberRepository
import kotlinx.coroutines.launch

class QuoteViewModel(private val repository: BookberRepository) : ViewModel() {

    private val _quotes: LiveData<List<QuoteEntity>> =
        repository.loadAllQuotes().distinctUntilChanged().switchMap { observerQuotes(it) }
    val quotes: LiveData<List<QuoteEntity>> = _quotes

    private val _categories: LiveData<List<CategoryEntity>> =
        repository.loadCategoriesByType(1).distinctUntilChanged()
            .switchMap { observerCategories(it) }
    val categories: LiveData<List<CategoryEntity>> = _categories

    val filteredQuotes: MutableLiveData<List<QuoteEntity>> = MutableLiveData()

    private fun observerQuotes(quoteResult: Result<List<QuoteEntity>>): LiveData<List<QuoteEntity>> {
        val result = MutableLiveData<List<QuoteEntity>>()

        if (quoteResult is Result.Success) {
            viewModelScope.launch {
                result.value = filterItems(quoteResult.data)
            }
        } else {
            result.value = emptyList()
        }

        return result
    }

    private fun filterItems(quotes: List<QuoteEntity>): List<QuoteEntity> {
        return quotes
    }

    private fun observerCategories(categoryResult: Result<List<CategoryEntity>>): LiveData<List<CategoryEntity>> {
        val result = MutableLiveData<List<CategoryEntity>>()

        if (categoryResult is Result.Success) {
            viewModelScope.launch {
                result.value = loadCategories(categoryResult.data)
            }
        } else {
            result.value = arrayListOf(CategoryEntity(category = "All", type = 1))
        }

        return result
    }

    private fun loadCategories(categories: List<CategoryEntity>): List<CategoryEntity> {
        val categoriesToShow = ArrayList<CategoryEntity>()

        categoriesToShow.add(0, CategoryEntity(category = "All", type = 1))
        categoriesToShow.addAll(categories.toList())

        return categoriesToShow
    }

    fun loadQuotes() = repository.loadAllQuotes()

    fun updateQuote(quote: QuoteEntity) {
        viewModelScope.launch {
            repository.updateQuote(quote)
        }
    }

    fun filterQuotesByCategory(categoryId: String) {
        viewModelScope.launch {
            repository.loadQuotesByCategory(categoryId).let { result ->
                if (result is Result.Success) {
                    filteredQuotes.value = showFilterResult(result.data)
                }
            }
        }
    }

    private fun showFilterResult(list: List<QuoteEntity>): List<QuoteEntity> {
        val result = ArrayList<QuoteEntity>()
        result.addAll(list)
        return result
    }
}