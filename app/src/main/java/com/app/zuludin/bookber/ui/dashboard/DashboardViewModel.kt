package com.app.zuludin.bookber.ui.dashboard

import androidx.lifecycle.ViewModel
import com.app.zuludin.bookber.domain.BookberRepository

class DashboardViewModel(private val bookberRepository: BookberRepository) : ViewModel() {
    fun getBooks() = bookberRepository.loadBookStore()
}