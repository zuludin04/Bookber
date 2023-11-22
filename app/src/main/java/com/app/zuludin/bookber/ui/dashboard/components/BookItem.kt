package com.app.zuludin.bookber.ui.dashboard.components

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.zuludin.bookber.data.local.entity.BookEntity

@Composable
fun BookItem(
    book: BookEntity,
    onClick: (BookEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val byteArray = Base64.decode(book.cover, Base64.DEFAULT)
    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

    Column(
        modifier = modifier
            .clickable { onClick(book) }
            .padding(8.dp),
    ) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
        Text(text = book.title)
        Text(text = book.genre)
    }
}

@Preview
@Composable
fun BookItemPreview() {
    BookItem(book = BookEntity(title = "Hall", genre = "Oalah"), {})
}