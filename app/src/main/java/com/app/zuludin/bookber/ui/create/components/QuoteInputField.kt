package com.app.zuludin.bookber.ui.create.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.zuludin.bookber.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuoteInputField(modifier: Modifier = Modifier, onSaveQuote: () -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue("")) }

    Card(shape = RoundedCornerShape(0.dp), elevation = CardDefaults.cardElevation(4.dp)) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(8.dp)
        ) {
            TextField(
                modifier = Modifier.weight(5f),
                value = text,
                onValueChange = {
                    text = it
                },
                placeholder = { Text(text = "Input Quote") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(onDone = {}),
                shape = RoundedCornerShape(100.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_attach),
                        contentDescription = null
                    )
                },
                maxLines = 3,
            )

            Spacer(modifier = Modifier.width(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clip(CircleShape)
                    .background(colorResource(id = R.color.colorAccent))
                    .clickable { onSaveQuote() },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Send, contentDescription = null)
            }
        }
    }
}

@Preview
@Composable
fun QuoteInputFieldPreview() {
    QuoteInputField(onSaveQuote = {})
}