package com.app.zuludin.bookber.ui.dashboard.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.zuludin.bookber.data.local.entity.BookEntity

@Composable
fun BookItem(
    book: BookEntity,
    onClick: (BookEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable { onClick(book) }
            .padding(8.dp),
    ) {
        Text(text = book.title)
        Text(text = book.genre)
    }
}

@Preview
@Composable
fun BookItemPreview() {
    BookItem(book = BookEntity(title = "Hall", genre = "Oalah"), {})
}