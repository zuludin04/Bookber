package com.app.zuludin.bookber.ui.quotedetail

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
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.zuludin.bookber.R
import com.app.zuludin.bookber.data.local.entity.BookEntity
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.util.getViewModelFactory

@Composable
fun QuoteDetailScreen(
    quoteId: String,
    onBack: () -> Unit,
    viewModel: QuoteDetailViewModel = viewModel(factory = getViewModelFactory()),
    state: QuoteDetailState = rememberQuoteDetailState(quoteId, viewModel)
) {

    val quote by viewModel.quoteDetail.observeAsState(initial = QuoteEntity())
    val category by viewModel.quoteCategory.observeAsState(initial = "")
    val bookInfo by viewModel.quoteBookInfo.observeAsState(initial = BookEntity())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                backgroundColor = Color.White,
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Share, null)
                    }

                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Edit, null)
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            QuoteBanner(quote, category)
            QuoteBookInfo(bookInfo)
            Spacer(modifier = Modifier.height(48.dp))
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                border = BorderStroke(2.dp, Color.Red),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Delete", color = Color.Red, fontSize = 16.sp)
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = null,
                        tint = Color.Red
                    )
                }
            }
        }
    }
}

@Composable
private fun QuoteBanner(quote: QuoteEntity, category: String) {
    Card(
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        ConstraintLayout(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
        ) {
            val (q, a, c) = createRefs()

            Text(
                text = category,
                fontSize = 12.sp,
                modifier = Modifier
                    .constrainAs(c) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                    .background(Color(0xffFEDBD0), RoundedCornerShape(10.dp))
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
private fun QuoteBookInfo(book: BookEntity?) {
    Card(
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
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
            val bookAvailable = book != null

            if (bookAvailable) {
                Image(
                    painter = painterResource(id = R.drawable.book_example),
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
                    text = if (bookAvailable) book?.title ?: "" else "No Book Info",
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (bookAvailable) book?.author ?: "" else "Please Input Book Info",
                    color = Color.Gray
                )
            }
            IconButton(onClick = { }, modifier = Modifier.constrainAs(removeBook) {
                end.linkTo(parent.end)
                top.linkTo(image.top)
                bottom.linkTo(image.bottom)
            }) {
                Icon(
                    painter = painterResource(id = if (bookAvailable) R.drawable.ic_remove else R.drawable.ic_add),
                    contentDescription = null,
                    tint = if (bookAvailable) Color.Red else Color.Black
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
        ), category = ""
    )
}

@Preview
@Composable
fun QuoteBookInfoPreview() {
    QuoteBookInfo(BookEntity())
}