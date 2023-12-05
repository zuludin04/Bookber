package com.app.zuludin.bookber.ui.quote.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.zuludin.bookber.R
import com.app.zuludin.bookber.data.local.entity.QuoteEntity

@Composable
fun QuoteItem(
    quote: QuoteEntity,
    onDeleteQuote: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showQuoteManagement by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = modifier
            .clickable { showQuoteManagement = true }
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_quote),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.clip(RoundedCornerShape(5.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = quote.quotes,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "- ${quote.author}",
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Gray
            )
        }
    }
    if (showQuoteManagement) {
        QuoteManagementSheet(
            quote = quote,
            onDismissRequest = { showQuoteManagement = !showQuoteManagement },
            onDeleteQuote = { onDeleteQuote(it) }
        )
    }
}

@Preview
@Composable
fun QuoteItemPreview() {
    QuoteItem(
        quote = QuoteEntity(
            quotes = "Giving absolutely everything doesn’t guarantee you get anything but it’s the only chance to get something.",
            author = "Jurgen Klopp"
        ),
        onDeleteQuote = {}
    )
}