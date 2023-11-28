package com.app.zuludin.bookber.ui.quote

import androidx.lifecycle.ViewModel
import com.app.zuludin.bookber.domain.BookberRepository

class QuoteViewModel(private val repository: BookberRepository) : ViewModel() {
    fun loadQuotes() = repository.loadAllQuotes()
}