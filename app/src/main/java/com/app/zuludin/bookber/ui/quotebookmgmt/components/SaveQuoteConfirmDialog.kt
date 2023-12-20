package com.app.zuludin.bookber.ui.quotebookmgmt.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
                shape = RoundedCornerShape(10.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            SampleSpinner(
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

@Composable
fun SampleSpinner(
    list: List<CategoryEntity>,
    preselected: String,
    onSelectionChanged: (myData: CategoryEntity) -> Unit,
    modifier: Modifier = Modifier,
    enableSpinner: Boolean = true,
    editBook: Boolean = false,
) {

    var selected by remember { mutableStateOf(CategoryEntity(category = preselected)) }
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Column {
            TextField(
                value = if (editBook) preselected else selected.category,
                onValueChange = { },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = { Icon(Icons.Outlined.ArrowDropDown, null) },
                readOnly = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(10.dp)
            )
            DropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                list.forEach { entry ->
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            selected = entry
                            expanded = false
                            onSelectionChanged(selected)
                        },
                        text = {
                            Text(
                                text = entry.category,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .align(Alignment.Start),
                                color = Color.Black
                            )
                        }
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .padding(10.dp)
                .clickable(
                    enabled = enableSpinner,
                    onClick = { expanded = !expanded }
                )
        )
    }
}