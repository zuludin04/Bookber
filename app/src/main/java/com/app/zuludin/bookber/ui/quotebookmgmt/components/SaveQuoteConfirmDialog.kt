package com.app.zuludin.bookber.ui.quotebookmgmt.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.data.local.entity.QuoteEntity
import com.app.zuludin.bookber.util.components.SelectCategorySpinner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveQuoteConfirmDialog(
    isUpdate: Boolean,
    quote: QuoteEntity?,
    category: CategoryEntity? = null,
    categories: List<CategoryEntity>,
    onSaveQuote: (quote: String, author: String, category: CategoryEntity) -> Unit,
    onDismissRequest: () -> Unit
) {
    var authorField by remember { mutableStateOf(TextFieldValue(quote?.author ?: "")) }
    var quoteField by remember { mutableStateOf(TextFieldValue(quote?.quotes ?: "")) }
    var selectedCategoryId by remember { mutableStateOf(quote?.categoryId ?: "") }
    var selectedCategory by remember { mutableStateOf(CategoryEntity()) }

    ModalBottomSheet(onDismissRequest = { onDismissRequest() }) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = if (isUpdate) "Update Quote" else "Save Quote",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = quoteField,
                onValueChange = { quoteField = it },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledTextColor = Color.Black,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(10.dp),
                enabled = isUpdate,
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = authorField,
                onValueChange = { authorField = it },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(10.dp),
                isError = authorField.text.isEmpty()
            )
            Spacer(modifier = Modifier.height(16.dp))

            SelectCategorySpinner(
                modifier = Modifier.fillMaxWidth(),
                list = categories,
                preselected = category?.category ?: "Select Category",
                onSelectionChanged = {
                    selectedCategoryId = it.id
                    selectedCategory = it
                },
            )
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val q = QuoteEntity(
                        quotes = quoteField.text,
                        author = authorField.text,
                        categoryId = selectedCategoryId,
                        bookId = quote?.bookId ?: ""
                    )

                    if (isUpdate) q.id = quote!!.id

                    onSaveQuote(quoteField.text, authorField.text, selectedCategory)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (isUpdate) "Update" else "Save")
            }
        }
    }
}