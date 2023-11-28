package com.app.zuludin.bookber.ui.category.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.app.zuludin.bookber.R

@Composable
fun CategoryItem(color: Color, category: String, modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
    ) {
        val (indicator, title, delete) = createRefs()

        Box(modifier = Modifier
            .constrainAs(indicator) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
            .padding(horizontal = 8.dp)
            .size(15.dp)
            .clip(CircleShape)
            .background(color))

        Text(text = category,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.constrainAs(title) {
                start.linkTo(indicator.end)
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
                .clickable { })
    }
}

@Preview
@Composable
fun CategoryItemPreview() {
    CategoryItem(color = Color.Yellow, category = "Fiction")
}