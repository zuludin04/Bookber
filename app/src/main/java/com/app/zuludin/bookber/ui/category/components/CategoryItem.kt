package com.app.zuludin.bookber.ui.category.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.app.zuludin.bookber.R
import com.app.zuludin.bookber.data.local.entity.CategoryEntity

@Composable
fun CategoryItem(
    category: CategoryEntity,
    onDeleteCategory: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val (title, delete) = createRefs()

        Text(text = category.category,
            fontSize = 16.sp,
            color = Color.Black.copy(alpha = 0.9f),
            modifier = Modifier.constrainAs(title) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            })

        Icon(painter = painterResource(id = R.drawable.ic_delete),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(delete) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .padding(horizontal = 8.dp)
                .clickable { onDeleteCategory(category.id) })
    }
}

@Preview
@Composable
fun CategoryItemPreview() {
    CategoryItem(
        category = CategoryEntity(category = "Fiction"),
        onDeleteCategory = {})
}