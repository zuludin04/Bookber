package com.app.zuludin.bookber.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.zuludin.bookber.domain.BookberRepository
import com.app.zuludin.bookber.ui.book.BookViewModel
import com.app.zuludin.bookber.ui.create.BookCreateViewModel
import com.app.zuludin.bookber.ui.dashboard.DashboardViewModel
import com.app.zuludin.bookber.ui.detail.BookDetailViewModel
import com.app.zuludin.bookber.ui.quote.QuoteViewModel

class ViewModelFactory constructor(
    private val bookberRepository: BookberRepository,
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(bookberRepository) as T
        } else if (modelClass.isAssignableFrom(BookCreateViewModel::class.java)) {
            return BookCreateViewModel(bookberRepository) as T
        } else if (modelClass.isAssignableFrom(BookDetailViewModel::class.java)) {
            return BookDetailViewModel(bookberRepository) as T
        } else if (modelClass.isAssignableFrom(QuoteViewModel::class.java)) {
            return QuoteViewModel(bookberRepository) as T
        } else if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
            return BookViewModel(bookberRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(ServiceLocator.provideBookberRepository(context))
        }.also { instance = it }
    }
}