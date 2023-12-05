package com.app.zuludin.bookber.ui.quote.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.zuludin.bookber.R
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import de.charlex.compose.RevealDirection
import de.charlex.compose.RevealSwipe

@Composable
fun QuoteItem(
    quote: QuoteEntity,
    onClick: (QuoteEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = modifier
            .clickable { onClick(quote) }
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
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeableQuoteItem(
    quote: QuoteEntity,
    onClick: (QuoteEntity) -> Unit,
    onRemoveFromBook: () -> Unit,
    onDeleteQuote: () -> Unit
) {
    RevealSwipe(
        directions = setOf(RevealDirection.EndToStart),
        hiddenContentEnd = {
            Row(Modifier.padding(horizontal = 16.dp)) {
                IconButton(
                    modifier = Modifier.size(56.dp),
                    onClick = onRemoveFromBook,
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_remove),
                            tint = Color.Gray,
                            contentDescription = null,
                        )
                    }
                )
                IconButton(
                    modifier = Modifier.size(56.dp),
                    onClick = onDeleteQuote,
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            tint = Color.Red,
                            contentDescription = null,
                        )
                    }
                )
            }
        },
        maxRevealDp = 128.dp,
        backgroundCardEndColor = colorResource(id = R.color.colorAccent),
        backgroundCardModifier = Modifier.padding(10.dp)
    ) {
        QuoteItem(quote = quote, onClick = onClick)
    }
}

@Preview
@Composable
fun QuoteItemPreview() {
    QuoteItem(
        quote = QuoteEntity(
            quotes = "Giving absolutely everything doesn’t guarantee you get anything but it’s the only chance to get something.",
            author = "Jurgen Klopp"
        ), onClick = {})
}