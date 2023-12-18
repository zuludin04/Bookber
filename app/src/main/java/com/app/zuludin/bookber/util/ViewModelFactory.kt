package com.app.zuludin.bookber.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.zuludin.bookber.BookberApplication
import com.app.zuludin.bookber.domain.BookberRepository
import com.app.zuludin.bookber.ui.book.BookViewModel
import com.app.zuludin.bookber.ui.category.CategoryViewModel
import com.app.zuludin.bookber.ui.quote.QuoteViewModel
import com.app.zuludin.bookber.ui.quotebookmgmt.QuoteBookManagementViewModel
import com.app.zuludin.bookber.ui.quotedetail.QuoteDetailViewModel

class ViewModelFactory constructor(
    private val repository: BookberRepository,
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuoteBookManagementViewModel::class.java)) {
            return QuoteBookManagementViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(QuoteViewModel::class.java)) {
            return QuoteViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
            return BookViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            return CategoryViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(QuoteDetailViewModel::class.java)) {
            return QuoteDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(application: BookberApplication): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(application.repository)
            }.also { instance = it }
    }
}