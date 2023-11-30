package com.app.zuludin.bookber.ui.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.app.zuludin.bookber.data.Result
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.domain.BookberRepository
import kotlinx.coroutines.launch

class BookViewModel(private val repository: BookberRepository) : ViewModel() {

    private val _books: LiveData<List<BookEntity>> =
        repository.loadBookStore().distinctUntilChanged().switchMap { observerBooks(it) }
    val books: LiveData<List<BookEntity>> = _books

    private val _categories: LiveData<List<CategoryEntity>> =
        repository.loadCategoriesByType(2).distinctUntilChanged()
            .switchMap { observerCategories(it) }
    val categories: LiveData<List<CategoryEntity>> = _categories

    private fun observerBooks(bookResult: Result<List<BookEntity>>): LiveData<List<BookEntity>> {
        val result = MutableLiveData<List<BookEntity>>()

        if (bookResult is Result.Success) {
            viewModelScope.launch {
                result.value = showBooks(bookResult.data)
            }
        } else {
            result.value = emptyList()
        }

        return result
    }

    private fun showBooks(books: List<BookEntity>) = books

    private fun observerCategories(categoriesResult: Result<List<CategoryEntity>>): LiveData<List<CategoryEntity>> {
        val result = MutableLiveData<List<CategoryEntity>>()

        if (categoriesResult is Result.Success) {
            viewModelScope.launch {
                result.value = showCategories(categoriesResult.data)
            }
        } else {
            result.value = arrayListOf(CategoryEntity(category = "All", type = 1))
        }

        return result
    }

    private fun showCategories(categories: List<CategoryEntity>): List<CategoryEntity> {
        val categoriesToShow = ArrayList<CategoryEntity>()

        categoriesToShow.add(0, CategoryEntity(category = "All", type = 1))
        categoriesToShow.addAll(categories.toList())

        return categoriesToShow
    }

    fun loadBooks() = repository.loadBookStore()
}