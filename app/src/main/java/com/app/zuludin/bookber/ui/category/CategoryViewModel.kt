package com.app.zuludin.bookber.ui.category

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.domain.BookberRepository
import com.app.zuludin.bookber.domain.model.Category
import com.app.zuludin.bookber.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CategoryScreenUiState(
    val quoteCategories: QuoteCategoryUiState,
    val bookCategories: BookCategoryUiState,
    val isError: Boolean = false,
)

@Immutable
sealed interface QuoteCategoryUiState {
    data class Success(val categories: List<Category>) : QuoteCategoryUiState
    data object Error : QuoteCategoryUiState
    data object Loading : QuoteCategoryUiState
}

@Immutable
sealed interface BookCategoryUiState {
    data class Success(val categories: List<Category>) : BookCategoryUiState
    data object Error : BookCategoryUiState
    data object Loading : BookCategoryUiState
}

@HiltViewModel
class CategoryViewModel @Inject constructor(private val repository: BookberRepository) :
    ViewModel() {

    private val quoteCategories: Flow<Result<List<Category>>> = repository.observeCategoryByType(1)
    private val bookCategories: Flow<Result<List<Category>>> = repository.observeCategoryByType(2)
    private val isError = MutableStateFlow(false)

    val uiState: StateFlow<CategoryScreenUiState> =
        combine(quoteCategories, bookCategories, isError) { quoteResult, bookResult, error ->
            val quoteCats = when (quoteResult) {
                is Result.Error -> QuoteCategoryUiState.Error
                Result.Loading -> QuoteCategoryUiState.Loading
                is Result.Success -> QuoteCategoryUiState.Success(quoteResult.data)
            }

            val bookCats = when (bookResult) {
                is Result.Error -> BookCategoryUiState.Error
                Result.Loading -> BookCategoryUiState.Loading
                is Result.Success -> BookCategoryUiState.Success(bookResult.data)
            }

            CategoryScreenUiState(quoteCats, bookCats, error)
        }.stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = CategoryScreenUiState(
                QuoteCategoryUiState.Loading,
                BookCategoryUiState.Loading,
                false
            )
        )

    fun saveNewCategory(category: CategoryEntity) {
        viewModelScope.launch {
            repository.saveCategory(category)
        }
    }

    fun deleteSelectedCategory(categoryId: String) {
        viewModelScope.launch {
            repository.deleteCategoryById(categoryId)
        }
    }
}