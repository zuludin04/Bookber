package com.app.zuludin.bookber.ui.quotedetail

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import com.app.zuludin.bookber.domain.model.Book
import com.app.zuludin.bookber.ui.book.BookItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectBookSheet(
    books: List<Book>,
    onDismissRequest: () -> Unit,
    onSelectBook: (Book) -> Unit
) {
    ModalBottomSheet(onDismissRequest = { onDismissRequest() }) {
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(books) { books ->
                BookItem(
                    book = books,
                    onClick = {
                        onSelectBook(it)
                    }
                )
            }
        }
    }
}