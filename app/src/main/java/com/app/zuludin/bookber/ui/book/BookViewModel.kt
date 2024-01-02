package com.app.zuludin.bookber.ui.book

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.domain.BookberRepository
import com.app.zuludin.bookber.domain.model.Book
import com.app.zuludin.bookber.domain.model.Category
import com.app.zuludin.bookber.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class BookScreenUiState(
    val books: BooksUiState,
    val categories: List<Category> = listOf(Category(name = "All", id = "-1")),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val filter: String = "-1"
)

@Immutable
sealed interface BooksUiState {
    data class Success(val books: List<Book>) : BooksUiState
    data object Error : BooksUiState
    data object Loading : BooksUiState
}

@HiltViewModel
class BookViewModel @Inject constructor(repository: BookberRepository) : ViewModel() {

    private val books: Flow<Result<List<Book>>> = repository.observeAllBooks()
    private val categories: Flow<Result<List<Category>>> = repository.observeCategoryByType(2)
    private val filterCategory = MutableStateFlow("-1")
    private val isLoading = MutableStateFlow(false)
    private val isError = MutableStateFlow(false)

    val uiState: StateFlow<BookScreenUiState> =
        combine(
            books,
            categories,
            filterCategory,
            isLoading,
            isError
        ) { bookResult, categoryResult, filter, loading, error ->
            val categories = mutableListOf<Category>()
            val books = when (bookResult) {
                is Result.Error -> BooksUiState.Error
                Result.Loading -> BooksUiState.Loading
                is Result.Success -> BooksUiState.Success(filterBooks(bookResult.data, filter))
            }

            when (categoryResult) {
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> categories.addAll(loadCategories(categoryResult.data))
            }

            BookScreenUiState(
                books,
                categories,
                isLoading = loading,
                isError = error,
                filter = filter
            )

        }.stateIn(
            scope = viewModelScope,
            initialValue = BookScreenUiState(
                BooksUiState.Loading,
                listOf(Category(name = "All", id = "-1")),
                isLoading = true,
                isError = false,
                filter = "-1"
            ),
            started = WhileUiSubscribed
        )

    fun filterBookByCategory(category: String) {
        filterCategory.value = category
    }

    private fun filterBooks(books: List<Book>, category: String): List<Book> {
        val showBooks = ArrayList<Book>()
        if (category == "-1") {
            showBooks.addAll(books)
        } else {
            val result = books.filter { it.categoryId == category }
            showBooks.addAll(result)
        }

        return showBooks
    }

    private fun loadCategories(categories: List<Category>): List<Category> {
        val categoriesToShow = ArrayList<Category>()

        categoriesToShow.add(0, Category(name = "All", id = "-1"))
        categoriesToShow.addAll(categories.toList())

        return categoriesToShow
    }
}