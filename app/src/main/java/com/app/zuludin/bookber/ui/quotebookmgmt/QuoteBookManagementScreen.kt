package com.app.zuludin.bookber.ui.quotebookmgmt

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.zuludin.bookber.R
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.domain.model.Quote
import com.app.zuludin.bookber.ui.quotebookmgmt.components.BookInformation
import com.app.zuludin.bookber.ui.quotebookmgmt.components.QuoteInputField
import com.app.zuludin.bookber.util.components.ConfirmAlertDialog
import com.app.zuludin.bookber.util.components.ManageQuoteSheet
import com.app.zuludin.bookber.util.components.QuoteItem
import com.app.zuludin.bookber.util.enums.BookInfoState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuoteBookManagementScreen(
    onBack: () -> Unit,
    onOpenDetailQuote: (String) -> Unit,
    onDeleteBook: () -> Unit,
    bookId: String?,
    bookState: String,
    viewModel: QuoteBookManagementViewModel = hiltViewModel(),
    state: QuoteBookManagementState = rememberQuoteBookManagementState(
        bookId,
        onDeleteBook,
        viewModel
    )
) {
    val bookInfoState = convertStringToBookState(bookState)

    var managementState by remember { mutableStateOf(bookInfoState) }
    var showSaveQuoteDialog by remember { mutableStateOf(false) }
    var showQuoteInput by remember { mutableStateOf(bookInfoState != BookInfoState.ADD_BOOK) }
    var quoteInput by remember { mutableStateOf("") }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }

    val quoteCategories by viewModel.quoteCategories.observeAsState(initial = emptyList())
    val quotesWithBook by viewModel.bookQuotes.observeAsState(initial = mutableListOf())
    val observeBookId by viewModel.bookId.observeAsState(initial = bookId)
    val quotesWithoutBook = remember { mutableStateListOf<QuoteEntity>() }

    Scaffold(
        contentColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                actions = {
                    if (observeBookId != null) {
                        IconButton(onClick = {
                            managementState = if (managementState == BookInfoState.DETAIL_BOOK) {
                                BookInfoState.ADD_BOOK
                            } else {
                                BookInfoState.DETAIL_BOOK
                            }
                        }) {
                            Icon(
                                if (managementState == BookInfoState.DETAIL_BOOK) Icons.Filled.Edit else Icons.Filled.Close,
                                null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        IconButton(onClick = { showDeleteConfirmDialog = true }) {
                            Icon(
                                painterResource(id = R.drawable.ic_delete),
                                null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        },
        bottomBar = {
            if (showQuoteInput) {
                QuoteInputField(onSaveQuote = {
                    quoteInput = it
                    showSaveQuoteDialog = true
                })
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            BookInformation(
                viewModel = viewModel,
                bookState = managementState,
                onSaveBook = {
                    viewModel.saveBook(quotesWithoutBook.toList())
                    showQuoteInput = true
                    managementState = BookInfoState.DETAIL_BOOK
                },
                onInputBook = { managementState = BookInfoState.ADD_BOOK }
            )

            LazyColumn {
                items(
                    if (bookInfoState == BookInfoState.ADD_QUOTE) quotesWithoutBook else quotesWithBook,
                    key = { q -> q.id }) { quote ->
                    QuoteItem(
                        quote = Quote(id = "", categoryId = "", bookId = ""),
                        onDetailQuote = onOpenDetailQuote,
                    )
                }
            }

            if (showSaveQuoteDialog) {
                ManageQuoteSheet(
                    isUpdate = false,
                    quote = QuoteEntity(quotes = quoteInput),
                    categories = quoteCategories,
                    onSaveQuote = { quote, author, category ->
                        val newQuote = QuoteEntity(
                            quotes = quote,
                            author = author,
                            categoryId = category.id
                        )

                        viewModel.saveQuote(newQuote)
                        if (bookInfoState == BookInfoState.ADD_QUOTE) quotesWithoutBook.add(newQuote)
                        showSaveQuoteDialog = false
                    },
                    onDismissRequest = { showSaveQuoteDialog = !showSaveQuoteDialog },
                )
            }

            if (showDeleteConfirmDialog) {
                ConfirmAlertDialog(
                    message = "Are you sure want to delete this book?",
                    onDismissRequest = { showDeleteConfirmDialog = false },
                    onConfirm = state::onDelete
                )
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