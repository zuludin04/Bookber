package com.app.zuludin.bookber.ui.quotebookmgmt

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun rememberQuoteBookManagementState(
    bookId: String?,
    onDeleteBook: () -> Unit,
    viewModel: QuoteBookManagementViewModel,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): QuoteBookManagementState {
    return remember(bookId, viewModel) {
        QuoteBookManagementState(bookId, onDeleteBook, viewModel, coroutineScope)
    }
}

@Stable
class QuoteBookManagementState(
    val bookId: String?,
    private val onDeleteBook: () -> Unit,
    private val viewModel: QuoteBookManagementViewModel,
    private val coroutineScope: CoroutineScope
) {
    init {
        if (bookId != null) {
            viewModel.start(bookId)
        }
    }

    fun onDelete() {
        coroutineScope.launch {
            viewModel.deleteBook()
        }
        onDeleteBook()
    }
}