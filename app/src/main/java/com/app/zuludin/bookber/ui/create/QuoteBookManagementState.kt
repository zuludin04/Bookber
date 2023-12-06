package com.app.zuludin.bookber.ui.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember

@Composable
fun rememberQuoteBookManagementState(
    bookId: String?,
    viewModel: BookCreateViewModel
): QuoteBookManagementState {
    return remember(bookId, viewModel) {
        QuoteBookManagementState(bookId, viewModel)
    }
}

@Stable
class QuoteBookManagementState(bookId: String?, viewModel: BookCreateViewModel) {
    init {
        if (bookId != null) {
            viewModel.start(bookId)
        }
    }
}