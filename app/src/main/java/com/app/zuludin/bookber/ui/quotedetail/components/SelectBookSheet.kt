package com.app.zuludin.bookber.ui.quotedetail.components

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.ui.book.components.BookItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectBookSheet(
    books: List<BookEntity>,
    onDismissRequest: () -> Unit,
    onSelectBook: (BookEntity) -> Unit
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