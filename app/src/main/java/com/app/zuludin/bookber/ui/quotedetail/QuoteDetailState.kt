package com.app.zuludin.bookber.ui.quotedetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember

@Composable
fun rememberQuoteDetailState(
    quoteId: String,
    viewModel: QuoteDetailViewModel
): QuoteDetailState {
    return remember(quoteId, viewModel) {
        QuoteDetailState(quoteId, viewModel)
    }
}

@Stable
class QuoteDetailState(quoteId: String, viewModel: QuoteDetailViewModel) {
    init {
        viewModel.start(quoteId)
    }
}