package com.app.zuludin.bookber.ui.quotebookmgmt

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember

@Composable
fun rememberQuoteBookManagementState(
    bookId: String?,
    viewModel: QuoteBookManagementViewModel
): QuoteBookManagementState {
    return remember(bookId, viewModel) {
        QuoteBookManagementState(bookId, viewModel)
    }
}

@Stable
class QuoteBookManagementState(bookId: String?, viewModel: QuoteBookManagementViewModel) {
    init {
        if (bookId != null) {
            viewModel.start(bookId)
        }


    }
}