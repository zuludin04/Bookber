package com.app.zuludin.bookber.ui.quote

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.domain.BookberRepository
import com.app.zuludin.bookber.domain.model.Category
import com.app.zuludin.bookber.domain.model.Quote
import com.app.zuludin.bookber.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class QuoteScreenUiState(
    val quotes: QuotesUiState,
    val categories: List<Category> = listOf(Category(name = "All", id = "-1")),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val filter: String = "-1"
)

@Immutable
sealed interface QuotesUiState {
    data class Success(val quotes: List<Quote>) : QuotesUiState
    data object Error : QuotesUiState
    data object Loading : QuotesUiState
}

@HiltViewModel
class QuoteViewModel @Inject constructor(repository: BookberRepository) : ViewModel() {

    private val quotes: Flow<Result<List<Quote>>> = repository.observeAllQuotes()
    private val categories: Flow<Result<List<Category>>> = repository.observeCategoryByType(1)
    private val filterCategory = MutableStateFlow("-1")
    private val isLoading = MutableStateFlow(false)
    private val isError = MutableStateFlow(false)

    val uiState: StateFlow<QuoteScreenUiState> =
        combine(
            quotes,
            categories,
            isLoading,
            isError,
            filterCategory
        ) { quoteResult, categoryResult, loading, error, filter ->
            val categories = mutableListOf<Category>()
            val quotes = when (quoteResult) {
                Result.Loading -> QuotesUiState.Loading
                is Result.Error -> QuotesUiState.Error
                is Result.Success -> QuotesUiState.Success(filterQuotes(quoteResult.data, filter))
            }

            when (categoryResult) {
                Result.Loading -> {}
                is Result.Error -> {}
                is Result.Success -> categories.addAll(loadCategories(categoryResult.data))
            }

            QuoteScreenUiState(
                quotes,
                categories,
                loading,
                error,
                filter
            )
        }
            .stateIn(
                scope = viewModelScope,
                initialValue = QuoteScreenUiState(
                    QuotesUiState.Loading,
                    listOf(Category(name = "All", id = "-1")),
                    isLoading = true,
                    isError = false,
                    filter = "-1"
                ),
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

    private fun loadCategories(categories: List<Category>): List<Category> {
        val categoriesToShow = ArrayList<Category>()

        categoriesToShow.add(0, Category(name = "All", id = "-1"))
        categoriesToShow.addAll(categories.toList())

        return categoriesToShow
    }
}