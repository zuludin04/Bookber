package com.app.zuludin.bookber.ui.quotedetail.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.ui.book.components.BookItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectBookSheet(onDismissRequest: () -> Unit, onSelectBook: (BookEntity) -> Unit) {
    ModalBottomSheet(onDismissRequest = { onDismissRequest() }) {
        LazyColumn {
            items(10) {
                BookItem(
                    book = BookEntity(
                        title = "Hall",
                        cover = "iVBORw0KGgoAAAANSUhEUgAAADMAAAAzCAYAAAA6oTAqAAAAEXRFWHRTb2Z0d2FyZQBwbmdjcnVzaEB1SfMAAABQSURBVGje7dSxCQBACARB+2/ab8BEeQNhFi6WSYzYLYudDQYGBgYGBgYGBgYGBgYGBgZmcvDqYGBgmhivGQYGBgYGBgYGBgYGBgYGBgbmQw+P/eMrC5UTVAAAAABJRU5ErkJggg=="
                    ),
                    onClick = {
                        onSelectBook(it)
                    }
                )
            }
        }
    }
}