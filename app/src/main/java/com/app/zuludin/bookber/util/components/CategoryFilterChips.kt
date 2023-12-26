package com.app.zuludin.bookber.util.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.util.bottomBorder

@Composable
fun CategoryFilterChips(categories: List<CategoryEntity>, onFilterQuote: (CategoryEntity) -> Unit) {
    var selectedChip by remember { mutableStateOf("All") }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .bottomBorder(1.dp, Color.Black.copy(alpha = 0.1f))
    ) {
        items(items = categories) { category ->
            CategoryChips(
                title = category.category,
                isSelected = selectedChip == category.category,
                onFilterCategory = {
                    selectedChip = it
                    onFilterQuote(category)
                }
            )
        }
    }
}

@Composable
private fun CategoryChips(title: String, isSelected: Boolean, onFilterCategory: (String) -> Unit) {
    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 8.dp)
            .border(
                width = 2.dp,
                color = if (isSelected) Color.Transparent else MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(100.dp)
            )
            .background(
                shape = RoundedCornerShape(100.dp),
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onFilterCategory(title) }
    ) {
        Text(
            title,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Preview
@Composable
fun CategoryFilterChipsPreview() {
    MaterialTheme {
        CategoryFilterChips(arrayListOf(CategoryEntity(category = "All"))) {}
    }
}