package com.app.zuludin.bookber.ui.quotebookmgmt

import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Base64
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.ui.quote.components.QuoteItem
import com.app.zuludin.bookber.ui.quotebookmgmt.components.BookInformation
import com.app.zuludin.bookber.ui.quotebookmgmt.components.QuoteInputField
import com.app.zuludin.bookber.ui.quotebookmgmt.components.SaveQuoteConfirmDialog
import com.app.zuludin.bookber.util.enums.BookInfoState
import com.app.zuludin.bookber.util.getViewModelFactory
import java.io.ByteArrayOutputStream

@Suppress("DEPRECATION")
@Composable
fun QuoteBookManagementScreen(
    onBack: () -> Unit,
    onOpenDetailQuote: (String) -> Unit,
    bookId: String?,
    bookState: String,
    viewModel: QuoteBookManagementViewModel = viewModel(factory = getViewModelFactory()),
    state: QuoteBookManagementState = rememberQuoteBookManagementState(bookId, viewModel)
) {
    val context = LocalContext.current
    val bookInfoState = convertStringToBookState(bookState)

    var managementState by remember { mutableStateOf(bookInfoState) }
    var showSaveQuoteDialog by remember { mutableStateOf(false) }
    var showQuoteInput by remember { mutableStateOf(bookInfoState != BookInfoState.ADD_BOOK) }
    var quoteInput by remember { mutableStateOf("") }
    var currentBookId by remember { mutableStateOf(bookId ?: "") }

    val quoteCategories by viewModel.quoteCategories.observeAsState(initial = emptyList())
    val quotesWithBook by viewModel.bookQuotes.observeAsState(initial = mutableListOf())
    val quotesWithoutBook = remember { mutableStateListOf<QuoteEntity>() }

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
                actions = {
                    IconButton(onClick = {
                        managementState = BookInfoState.ADD_BOOK
                    }) {
                        Icon(Icons.Filled.Edit, null)
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
                onSaveBook = { title, author, categoryId, imageUri ->
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
                    val stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                    val bytes = stream.toByteArray()
                    val bookCoverImage = Base64.encodeToString(bytes, Base64.DEFAULT)

                    val book = BookEntity(
                        title = title,
                        author = author,
                        cover = bookCoverImage,
                        categoryId = categoryId
                    )

                    currentBookId = book.id
                    if (bookId != null) {
                        book.id = bookId
                        viewModel.updateBook(book)
                    } else {
                        viewModel.saveBook(book, quotesWithoutBook.toList(), book.id)
                    }
                    if (bookInfoState != BookInfoState.ADD_QUOTE) viewModel.setBookId(book.id)
                    showQuoteInput = true
                    managementState = BookInfoState.DETAIL_BOOK
                }
            )

            LazyColumn {
                items(
                    if (bookInfoState == BookInfoState.ADD_QUOTE) quotesWithoutBook else quotesWithBook,
                    key = { q -> q.id }) { quote ->
                    QuoteItem(
                        quote = quote,
                        onDetailQuote = onOpenDetailQuote,
                    )
                }
            }

            if (showSaveQuoteDialog) {
                SaveQuoteConfirmDialog(
                    isUpdate = false,
                    quote = QuoteEntity(quotes = quoteInput),
                    categories = quoteCategories,
                    onSaveQuote = { quote, author, categoryId ->
                        val newQuote = QuoteEntity(
                            quotes = quote,
                            author = author,
                            categoryId = categoryId,
                            bookId = currentBookId
                        )

                        viewModel.saveQuote(newQuote)
                        if (bookInfoState == BookInfoState.ADD_QUOTE) quotesWithoutBook.add(newQuote)
                        showSaveQuoteDialog = false
                    },
                    onDismissRequest = { showSaveQuoteDialog = !showSaveQuoteDialog },
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