package com.app.zuludin.bookber.ui.quotebookmgmt.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.app.zuludin.bookber.R
import com.app.zuludin.bookber.domain.model.Book
import com.app.zuludin.bookber.domain.model.Category
import com.app.zuludin.bookber.util.components.SelectCategorySpinner
import com.app.zuludin.bookber.util.enums.BookInfoState
import java.io.ByteArrayOutputStream

@Composable
fun BookInformation(
    book: Book,
    category: Category?,
    categories: List<Category>,
    bookState: BookInfoState,
    onSaveBook: (Book) -> Unit,
    onInputBook: () -> Unit
) {
    if (bookState == BookInfoState.ADD_QUOTE) {
        BookEmptyInformation(onInputBook)
    } else {
        ShowBookInformation(book, category, categories, bookState, onSaveBook)
    }
}

@Composable
private fun BookEmptyInformation(inputBook: () -> Unit) {
    Card(shape = RoundedCornerShape(0.dp)) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(100.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { inputBook() },
            ) {
                Image(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(id = R.drawable.ic_books),
                    contentDescription = null
                )
                Text(text = "Add Book Info", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            }
        }
    }
}

@Suppress("DEPRECATION")
@Composable
private fun ShowBookInformation(
    book: Book,
    category: Category?,
    categories: List<Category>,
    bookState: BookInfoState,
    onSaveBook: (Book) -> Unit
) {
    val context = LocalContext.current

    var titleField by mutableStateOf(book.title)
    var authorField by mutableStateOf(book.author)
    var categoryField by mutableStateOf(category)
    var imageField by mutableStateOf(book.cover)

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var showPickImageSheet by remember { mutableStateOf(false) }

    Card(shape = RoundedCornerShape(0.dp), modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Card(
                    shape = RoundedCornerShape(5.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    modifier = Modifier
                        .width(100.dp)
                        .height(150.dp)
                        .clickable(
                            enabled = bookState != BookInfoState.DETAIL_BOOK,
                            onClick = { showPickImageSheet = true }
                        )
                ) {
                    if (imageUri != null) {
                        val painter = rememberAsyncImagePainter(
                            ImageRequest
                                .Builder(LocalContext.current)
                                .data(data = imageUri)
                                .build()
                        )
                        Image(
                            painter = painter,
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .clip(RoundedCornerShape(5.dp))
                                .width(100.dp)
                                .height(150.dp)
                        )
                    } else {
                        if (imageField != "") {
                            val byteArray = Base64.decode(imageField, Base64.DEFAULT)
                            val bitmap =
                                BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(5.dp))
                                    .width(100.dp)
                                    .height(150.dp)
                            )
                        } else {
                            Column(
                                modifier = Modifier
                                    .background(Color.Transparent)
                                    .border(
                                        width = 2.dp,
                                        shape = RoundedCornerShape(5.dp),
                                        color = Color.Black.copy(alpha = 0.5f)
                                    )
                                    .width(100.dp)
                                    .height(150.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_books),
                                    contentDescription = null,
                                    modifier = Modifier.size(28.dp)
                                )
                                Text(text = "Select Image", fontSize = 12.sp)
                            }
                        }
                    }
                }

                if (showPickImageSheet) {
                    PickImageBottomSheet(
                        onDismiss = {
                            showPickImageSheet = !showPickImageSheet
                        },
                        onSelectImage = { uri ->
                            imageUri = uri

                            val bitmap =
                                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                            val stream = ByteArrayOutputStream()
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                            val bytes = stream.toByteArray()
                            val bookCoverImage = Base64.encodeToString(bytes, Base64.DEFAULT)

                            imageField = bookCoverImage
                        }
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    TextField(
                        value = titleField,
                        label = { Text(text = "Title") },
                        onValueChange = { titleField = it },
                        enabled = bookState != BookInfoState.DETAIL_BOOK,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            errorContainerColor = Color.White,
                            disabledTextColor = Color.Black,
                            disabledIndicatorColor = Color.Transparent,
                        ),
                        shape = RoundedCornerShape(10.dp),
                        isError = titleField.isEmpty()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    TextField(
                        value = authorField,
                        label = { Text(text = "Author") },
                        onValueChange = { authorField = it },
                        enabled = bookState != BookInfoState.DETAIL_BOOK,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            errorContainerColor = Color.White,
                            disabledTextColor = Color.Black,
                            disabledIndicatorColor = Color.Transparent,
                        ),
                        shape = RoundedCornerShape(10.dp),
                        isError = authorField.isEmpty()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    SelectCategorySpinner(
                        modifier = Modifier.fillMaxWidth(),
                        list = categories,
                        preselected = categoryField?.name ?: "Select Category",
                        onSelectionChanged = { categoryField = it },
                        enableSpinner = bookState != BookInfoState.DETAIL_BOOK,
                        editBook = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (bookState == BookInfoState.ADD_BOOK) {
                Button(
                    onClick = {
                        if (titleField.isNotEmpty() && authorField.isNotEmpty() && imageField.isNotEmpty() && categoryField?.id != "" &&
                            categoryField?.name != "Select Category" &&
                            categoryField?.name != ""
                        ) {
                            val newBook = Book(
                                title = titleField,
                                author = authorField,
                                cover = imageField,
                                categoryId = categoryField?.id ?: ""
                            )
                            onSaveBook(newBook)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Save")
                }
            }
        }
    }
}