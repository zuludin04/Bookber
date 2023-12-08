package com.app.zuludin.bookber.ui.create

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.ui.create.components.BookInformation
import com.app.zuludin.bookber.ui.create.components.QuoteInputField
import com.app.zuludin.bookber.ui.create.components.SaveQuoteConfirmDialog
import com.app.zuludin.bookber.ui.quote.components.QuoteItem
import com.app.zuludin.bookber.util.enums.BookInfoState
import com.app.zuludin.bookber.util.getViewModelFactory
import java.io.ByteArrayOutputStream

@Suppress("DEPRECATION")
@Composable
fun QuoteBookManagementScreen(
    onBack: () -> Unit,
    bookId: String?,
    bookState: String,
    viewModel: BookCreateViewModel = viewModel(factory = getViewModelFactory()),
    state: QuoteBookManagementState = rememberQuoteBookManagementState(bookId, viewModel)
) {
    val context = LocalContext.current

    val bookInfoState = convertStringToBookState(bookState)
    var showSaveQuoteDialog by remember { mutableStateOf(false) }
    var showQuoteInput by remember { mutableStateOf(bookInfoState != BookInfoState.ADD_BOOK) }
    var quoteInput by remember { mutableStateOf("") }

    val quoteCategories by viewModel.quoteCategories.observeAsState(initial = emptyList())
    val quotes by viewModel.bookQuotes.observeAsState(initial = emptyList())
    val mutableQuotes = remember { quotes.toMutableStateList() }

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
                bookState = bookInfoState,
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
                    viewModel.saveBook(book)
                    showQuoteInput = true
                }
            )

            LazyColumn {
                items(mutableQuotes) { quote ->
                    QuoteItem(
                        quote = quote,
                        onDeleteQuote = {},
                        onRemoveFromBook = {},
                        onEditQuote = {}
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
                            bookId = ""
                        )

                        viewModel.saveQuote(newQuote)
                        mutableQuotes.add(newQuote)
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