package com.app.zuludin.bookber.ui.category

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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.app.zuludin.bookber.domain.model.Category
import com.app.zuludin.bookber.util.components.SelectCategorySpinner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryManagementSheet(
    category: String = "",
    type: String,
    onSaveCategory: (Category) -> Unit,
    onDismissRequest: () -> Unit
) {

    val categories = listOf(Category(name = "Quote"), Category(name = "Book"))

    var categoryField by remember { mutableStateOf(TextFieldValue(category)) }
    var categoryType by remember { mutableStateOf(type) }

    ModalBottomSheet(onDismissRequest = { onDismissRequest() }) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = categoryField,
                onValueChange = { categoryField = it },
                label = { Text(text = "Category") },
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

            SelectCategorySpinner(
                modifier = Modifier.fillMaxWidth(),
                list = categories,
                preselected = type,
                onSelectionChanged = { categoryType = it.name },
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val selectedType = if (categoryType == "Quote") 1 else 2
                    val cat = Category(name = categoryField.text, type = selectedType)
                    onSaveCategory(cat)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Save")
            }
        }
    }
}