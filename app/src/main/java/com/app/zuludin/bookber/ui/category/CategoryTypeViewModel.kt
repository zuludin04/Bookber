package com.app.zuludin.bookber.ui.category

import androidx.lifecycle.ViewModel
import com.app.zuludin.bookber.domain.BookberRepository

class CategoryTypeViewModel(private val repository: BookberRepository) : ViewModel() {
    fun getCategories(type: Int) = repository.loadCategoriesByType(type)
}