package com.app.zuludin.bookber.ui.quote.components

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.app.zuludin.bookber.R
import com.app.zuludin.bookber.data.local.entity.QuoteEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuoteManagementSheet(
    quote: QuoteEntity,
    onDismissRequest: () -> Unit,
    onDeleteQuote: (quoteId: String) -> Unit,
    onRemoveFromBook: (quote: QuoteEntity) -> Unit,
    onEditQuote: () -> Unit
) {
    val context = LocalContext.current

    ModalBottomSheet(onDismissRequest = { onDismissRequest() }) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_quote),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = quote.quotes,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(text = quote.author)
                }
            }
            Divider(modifier = Modifier.padding(vertical = 16.dp))
            QuoteManagementMenu(
                iconId = R.drawable.ic_edit,
                title = "Edit",
                onClickMenu = {
                    onEditQuote()
                    onDismissRequest()
                })
            QuoteManagementMenu(
                iconId = R.drawable.ic_share,
                title = "Share",
                onClickMenu = {
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
                    onDismissRequest()
                }
            )
            if (quote.bookId != "") {
                QuoteManagementMenu(
                    iconId = R.drawable.ic_remove,
                    title = "Remove from Book",
                    onClickMenu = {
                        onDismissRequest()
                        onRemoveFromBook(quote)
                    }
                )
            } else {
                QuoteManagementMenu(
                    iconId = R.drawable.ic_add,
                    title = "Add to Book",
                    onClickMenu = {}
                )
            }
            QuoteManagementMenu(
                iconId = R.drawable.ic_delete,
                title = "Delete",
                onClickMenu = {
                    onDismissRequest()
                    onDeleteQuote(quote.id)
                }
            )
        }
    }
}

@Composable
fun QuoteManagementMenu(iconId: Int, title: String, onClickMenu: () -> Unit) {
    Row(modifier = Modifier
        .padding(vertical = 8.dp)
        .clickable { onClickMenu() }
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = title, style = MaterialTheme.typography.bodyLarge)
    }
}