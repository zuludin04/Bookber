package com.app.zuludin.bookber.ui.create.components

import android.net.Uri
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.app.zuludin.bookber.R
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.ui.create.BookCreateViewModel
import com.app.zuludin.bookber.util.enums.BookInfoState

@Composable
fun BookInformation(
    viewModel: BookCreateViewModel,
    bookState: BookInfoState,
    onSaveBook: (String, String, String, Uri?) -> Unit
) {
    var showBookInfo by remember { mutableStateOf(bookState) }

    if (showBookInfo == BookInfoState.ADD_QUOTE) {
        BookEmptyInformation { showBookInfo = BookInfoState.ADD_BOOK }
    } else {
        ShowBookInformation(viewModel, showBookInfo, onSaveBook)
    }
}

@Composable
fun BookEmptyInformation(inputBook: () -> Unit) {
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

@Composable
fun ShowBookInformation(
    viewModel: BookCreateViewModel,
    bookState: BookInfoState,
    onSaveBook: (String, String, String, Uri?) -> Unit
) {
    val bookDetail by viewModel.bookDetail.observeAsState()

    var titleField by remember {
        mutableStateOf(TextFieldValue(bookDetail?.bookEntity?.title ?: ""))
    }
    var authorField by remember {
        mutableStateOf(TextFieldValue(bookDetail?.bookEntity?.author ?: ""))
    }
    var selectedCategoryId by remember { mutableStateOf("") }

    val categories by viewModel.bookCategories.observeAsState(initial = emptyList())
    val preselectCategory = if (bookState == BookInfoState.DETAIL_BOOK) CategoryEntity(
        category = bookDetail?.category?.category ?: ""
    ) else CategoryEntity(category = "Select Category")

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var showPickImageSheet by remember { mutableStateOf(false) }
    var bookAvailability by remember { mutableStateOf(bookState) }

    Card(shape = RoundedCornerShape(0.dp), modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.padding(16.dp)) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Card(
                        shape = RoundedCornerShape(5.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                        modifier = Modifier
                            .width(100.dp)
                            .height(150.dp)
                            .clickable(
                                enabled = bookAvailability != BookInfoState.DETAIL_BOOK,
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
                            Image(
                                painter = painterResource(id = R.drawable.book_example),
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(5.dp))
                                    .width(100.dp)
                                    .height(150.dp)
                            )
                        }
                    }

                    if (showPickImageSheet) {
                        PickImageBottomSheet(
                            onDismiss = {
                                showPickImageSheet = !showPickImageSheet
                            },
                            onSelectImage = { uri ->
                                imageUri = uri
                            }
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        TextField(
                            value = titleField,
                            onValueChange = { titleField = it },
                            enabled = bookAvailability != BookInfoState.DETAIL_BOOK
                        )
                        TextField(
                            value = authorField,
                            onValueChange = { authorField = it },
                            enabled = bookAvailability != BookInfoState.DETAIL_BOOK
                        )
                        SampleSpinner(
                            modifier = Modifier.fillMaxWidth(),
                            list = categories,
                            preselected = preselectCategory,
                            onSelectionChanged = {
                                selectedCategoryId = it.id
                            },
                            enableSpinner = bookAvailability != BookInfoState.DETAIL_BOOK
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (bookAvailability == BookInfoState.ADD_BOOK) {
                    Button(
                        onClick = {
                            onSaveBook(
                                titleField.text,
                                authorField.text,
                                selectedCategoryId,
                                imageUri
                            )
                            bookAvailability = BookInfoState.DETAIL_BOOK
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }
}