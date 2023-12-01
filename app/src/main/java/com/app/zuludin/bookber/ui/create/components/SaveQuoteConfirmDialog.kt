package com.app.zuludin.bookber.ui.create.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.app.zuludin.bookber.ui.create.BookCreateViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveQuoteConfirmDialog(
    viewModel: BookCreateViewModel,
    onDismissRequest: () -> Unit,
    onSaveQuote: (String) -> Unit
) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    val categories by viewModel.quoteCategories.observeAsState(initial = emptyList())

    ModalBottomSheet(onDismissRequest = { onDismissRequest() }) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Save Quote", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                IconButton(onClick = { onDismissRequest() }) {
                    Icon(Icons.Filled.Close, contentDescription = null)
                }
            }

            Text(text = "Giving absolutely everything doesn’t guarantee you get anything but it’s the only chance to get something.")
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = { text = it },
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
                preselected = CategoryEntity(category = "Select Category"),
                onSelectionChanged = {},
            )
            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { onSaveQuote(text.text) }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Save")
            }
        }
    }
}

@Composable
fun SampleSpinner(
    list: List<CategoryEntity>,
    preselected: CategoryEntity,
    onSelectionChanged: (myData: CategoryEntity) -> Unit,
    modifier: Modifier = Modifier
) {

    var selected by remember { mutableStateOf(preselected) }
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Column {
            TextField(
                value = selected.category,
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
                    onClick = { expanded = !expanded }
                )
        )
    }
}