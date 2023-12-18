package com.app.zuludin.bookber.ui.quotedetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun rememberQuoteDetailState(
    quoteId: String,
    onDeleteQuote: () -> Unit,
    viewModel: QuoteDetailViewModel,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): QuoteDetailState {
    return remember(quoteId, viewModel) {
        QuoteDetailState(quoteId, onDeleteQuote, coroutineScope, viewModel)
    }
}

@Stable
class QuoteDetailState(
    quoteId: String,
    private val onDeleteQuote: () -> Unit,
    private val coroutineScope: CoroutineScope,
    private val viewModel: QuoteDetailViewModel,
) {
    init {
        viewModel.start(quoteId)
    }

    fun onDelete() {
        coroutineScope.launch {
            viewModel.deleteQuote()
        }
        onDeleteQuote()
    }
}