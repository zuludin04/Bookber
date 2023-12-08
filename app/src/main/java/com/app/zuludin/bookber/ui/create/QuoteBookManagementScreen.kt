package com.app.zuludin.bookber.ui.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.ui.create.components.BookInformation
import com.app.zuludin.bookber.ui.create.components.QuoteInputField
import com.app.zuludin.bookber.ui.quote.components.QuoteItem
import com.app.zuludin.bookber.util.enums.BookInfoState
import com.app.zuludin.bookber.util.getViewModelFactory

@Composable
fun QuoteBookManagementScreen(
    onBack: () -> Unit,
    bookId: String?,
    bookState: String,
    viewModel: BookCreateViewModel = viewModel(factory = getViewModelFactory()),
    state: QuoteBookManagementState = rememberQuoteBookManagementState(bookId, viewModel)
) {
    val showQuoteField = remember { mutableStateOf(true) }
    val quotes by viewModel.bookQuotes.observeAsState(initial = emptyList())
    val q = remember { quotes.toMutableStateList() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Management") },
                backgroundColor = Color.White,
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        },
        bottomBar = {
            QuoteInputField(onSaveQuote = {

            })
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            BookInformation(
                viewModel = viewModel,
                bookState = convertStringToBookState(bookState),
                onSaveBook = { title, author, categoryId, imageUri ->

                }
            )

            LazyColumn {
                items(q) { quote ->
                    QuoteItem(
                        quote = quote,
                        onDeleteQuote = {},
                        onRemoveFromBook = {},
                        onEditQuote = { }
                    )
                }
            }
        }
    }
}

private fun convertStringToBookState(state: String): BookInfoState {
    return when (state) {
        BookInfoState.ADD_QUOTE.name -> BookInfoState.ADD_QUOTE
        BookInfoState.ADD_BOOK.name -> BookInfoState.ADD_BOOK
        BookInfoState.DETAIL_BOOK.name -> BookInfoState.DETAIL_BOOK
        else -> BookInfoState.ADD_QUOTE
    }
}