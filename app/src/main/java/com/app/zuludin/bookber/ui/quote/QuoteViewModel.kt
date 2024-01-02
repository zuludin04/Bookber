package com.app.zuludin.bookber.ui.quote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.domain.BookberRepository
import com.app.zuludin.bookber.domain.model.Quote
import com.app.zuludin.bookber.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class QuoteUiState(
    val quotes: List<Quote> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val filter: String = "-1"
)

@HiltViewModel
class QuoteViewModel @Inject constructor(private val repository: BookberRepository) : ViewModel() {

    private val quotes: Flow<Result<List<Quote>>> = repository.observeAllQuotes()
    private val filterCategory = MutableStateFlow("-1")
    private val isLoading = MutableStateFlow(false)
    private val isError = MutableStateFlow(false)

    private val _categories: LiveData<List<CategoryEntity>> =
        repository.loadCategoriesByType(1).distinctUntilChanged()
            .switchMap { observerCategories(it) }
    val categories: LiveData<List<CategoryEntity>> = _categories

    val uiState: StateFlow<QuoteUiState> =
        combine(quotes, isLoading, isError, filterCategory) { quoteResult, loading, error, filter ->
            when (quoteResult) {
                Result.Loading -> QuoteUiState(isLoading = true)
                is Result.Error -> QuoteUiState(isError = true)
                is Result.Success -> {
                    QuoteUiState(
                        quotes = filterQuotes(quoteResult.data, filter),
                        isLoading = loading,
                        isError = error,
                        filter = filter
                    )
                }
            }
        }
            .stateIn(
                scope = viewModelScope,
                initialValue = QuoteUiState(isLoading = true, filter = "-1"),
                started = WhileUiSubscribed
            )

    fun filterQuoteByCategory(category: String) {
        filterCategory.value = category
    }

    private fun filterQuotes(quotes: List<Quote>, category: String): List<Quote> {
        val showQuotes = ArrayList<Quote>()
        if (category == "-1") {
            showQuotes.addAll(quotes)
        } else {
            val result = quotes.filter { it.categoryId == category }
            showQuotes.addAll(result)
        }

        return showQuotes
    }

    private fun observerCategories(categoryResult: Result<List<CategoryEntity>>): LiveData<List<CategoryEntity>> {
        val result = MutableLiveData<List<CategoryEntity>>()

        if (categoryResult is Result.Success) {
            viewModelScope.launch {
                result.value = loadCategories(categoryResult.data)
            }
        } else {
            result.value = arrayListOf(CategoryEntity(category = "All", type = 1, id = "-1"))
        }

        return result
    }

    private fun loadCategories(categories: List<CategoryEntity>): List<CategoryEntity> {
        val categoriesToShow = ArrayList<CategoryEntity>()

        categoriesToShow.add(0, CategoryEntity(category = "All", type = 1, id = "-1"))
        categoriesToShow.addAll(categories.toList())

        return categoriesToShow
    }
}