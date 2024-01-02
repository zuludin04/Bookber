package com.app.zuludin.bookber.ui.quotedetail

import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.zuludin.bookber.R
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.theme.poppinsFamily
import com.app.zuludin.bookber.util.components.ConfirmAlertDialog
import com.app.zuludin.bookber.util.components.ManageQuoteSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuoteDetailScreen(
    quoteId: String,
    onBack: () -> Unit,
    onDeleteQuote: () -> Unit,
    viewModel: QuoteDetailViewModel = hiltViewModel(),
    state: QuoteDetailState = rememberQuoteDetailState(quoteId, onDeleteQuote, viewModel)
) {
    val context = LocalContext.current

    var showEditQuoteDialog by remember { mutableStateOf(false) }
    var showBookSelectDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    val quote by viewModel.quoteDetail.observeAsState(initial = QuoteEntity())
    val category by viewModel.quoteCategory.observeAsState(initial = CategoryEntity())
    val bookInfo by viewModel.quoteBookInfo.observeAsState(initial = BookEntity())
    val bookImage by viewModel.bookImage.observeAsState(initial = "")

    val categories by viewModel.categories.observeAsState(initial = emptyList())
    val books by viewModel.books.observeAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val intent = Intent(Intent.ACTION_SEND)
                        intent.type = "text/plain"
                        val quoteFormat = """
                        ${quote.quotes}
                        
                        - ${quote.author}
                    """.trimIndent()
                        intent.putExtra(Intent.EXTRA_TEXT, quoteFormat)
                        context.startActivity(
                            Intent.createChooser(intent, "Share Quote from ${quote.author}")
                        )
                    }) {
                        Icon(Icons.Filled.Share, null)
                    }

                    IconButton(onClick = {
                        showEditQuoteDialog = true
                    }) {
                        Icon(Icons.Filled.Edit, null)
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            QuoteBanner(quote, category)
            if (bookInfo != null) {
                QuoteBookInfo(bookInfo, bookImage) { viewModel.removeBookInfo() }
            } else {
                EmptyBookInfo { showBookSelectDialog = true }
            }
            Spacer(modifier = Modifier.height(48.dp))
            Button(
                onClick = { showDeleteDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.error),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Delete",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 16.sp,
                        fontFamily = poppinsFamily
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }

        if (showEditQuoteDialog) {
            ManageQuoteSheet(
                isUpdate = true,
                quote = quote,
                category = category,
                categories = categories,
                onSaveQuote = { quote, author, category ->
                    viewModel.updateQuote(quote, author, category)
                    showEditQuoteDialog = false
                },
                onDismissRequest = { showEditQuoteDialog = false }
            )
        }

        if (showBookSelectDialog) {
            SelectBookSheet(
                books = books,
                onDismissRequest = { showBookSelectDialog = false },
                onSelectBook = { book ->
                    viewModel.addBookInfo(book)
                    showBookSelectDialog = false
                }
            )
        }

        if (showDeleteDialog) {
            ConfirmAlertDialog(
                message = "Are you sure want to delete this quote?",
                onDismissRequest = { showDeleteDialog = false },
                onConfirm = state::onDelete
            )
        }
    }
}

@Composable
private fun QuoteBanner(quote: QuoteEntity, category: CategoryEntity?) {
    Card(
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        ConstraintLayout(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            val (q, a, c) = createRefs()

            Text(
                text = category?.category ?: "-",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier
                    .constrainAs(c) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                    .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(10.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            )
            Text(
                text = quote.quotes,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(q) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(top = 24.dp)
            )
            Text(
                text = "- ${quote.author}",
                color = Color.DarkGray.copy(alpha = 0.5f),
                modifier = Modifier
                    .constrainAs(a) {
                        top.linkTo(q.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(top = 24.dp)
            )
        }
    }
}

@Composable
private fun QuoteBookInfo(book: BookEntity?, bookImage: String, onRemoveBook: () -> Unit) {
    Card(
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        ConstraintLayout(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                .fillMaxWidth()
        ) {
            val (image, infoContainer, removeBook) = createRefs()

            if (bookImage != "") {
                val byteArray = Base64.decode(bookImage, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .constrainAs(image) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                        }
                        .height(80.dp)
                        .width(80.dp)
                )
            }
            Column(
                modifier = Modifier
                    .constrainAs(infoContainer) {
                        start.linkTo(image.end)
                        top.linkTo(image.top)
                        bottom.linkTo(image.bottom)
                    }
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = book?.title ?: "",
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = book?.author ?: "",
                    color = Color.Gray
                )
            }
            IconButton(onClick = { onRemoveBook() }, modifier = Modifier.constrainAs(removeBook) {
                end.linkTo(parent.end)
                top.linkTo(image.top)
                bottom.linkTo(image.bottom)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_remove),
                    contentDescription = null,
                    tint = Color.Red
                )
            }
        }
    }
}

@Composable
private fun EmptyBookInfo(onInputBook: () -> Unit) {
    Card(
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        ConstraintLayout(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                .fillMaxWidth()
        ) {
            val (image, infoContainer, removeBook) = createRefs()

            Column(
                modifier = Modifier
                    .constrainAs(infoContainer) {
                        start.linkTo(image.end)
                        top.linkTo(image.top)
                        bottom.linkTo(image.bottom)
                    }
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = "No Book Info",
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Please Input Book Info",
                    color = Color.Gray
                )
            }
            IconButton(onClick = { onInputBook() }, modifier = Modifier.constrainAs(removeBook) {
                end.linkTo(parent.end)
                top.linkTo(image.top)
                bottom.linkTo(image.bottom)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }
    }
}

@Preview
@Composable
fun QuoteBannerPreview() {
    QuoteBanner(
        quote = QuoteEntity(
            quotes = "Giving absolutely everything doesn’t guarantee you get anything but it’s the only chance to get something.",
            author = "Jurgen Klopp"
        ), category = CategoryEntity()
    )
}

@Preview
@Composable
fun QuoteBookInfoPreview() {
    QuoteBookInfo(BookEntity(), "") {}
}