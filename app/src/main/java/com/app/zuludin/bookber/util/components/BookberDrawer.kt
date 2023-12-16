package com.app.zuludin.bookber.util.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DrawerState
import androidx.compose.material.Icon
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.app.zuludin.bookber.R
import com.app.zuludin.bookber.navs.BookberDestination
import com.app.zuludin.bookber.navs.BookberNavigationActions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BookberModalDrawer(
    drawerState: DrawerState,
    currentRoute: String,
    navigationActions: BookberNavigationActions,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    content: @Composable () -> Unit,
) {
    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            BookberDrawer(
                currentRoute = currentRoute,
                navigateToQuotes = { navigationActions.navigateToQuotes() },
                navigateToBooks = { navigationActions.navigateToBooks() },
                navigateToCategory = { navigationActions.navigateToCategory() },
                navigateToFavorite = { navigationActions.navigateToFavorite() },
                navigateToWidget = {},
                navigateToSettings = {},
                closeDrawer = { coroutineScope.launch { drawerState.close() } },
            )
        }
    ) {
        content()
    }
}

@Composable
private fun BookberDrawer(
    currentRoute: String,
    navigateToQuotes: () -> Unit,
    navigateToBooks: () -> Unit,
    navigateToCategory: () -> Unit,
    navigateToFavorite: () -> Unit,
    navigateToWidget: () -> Unit,
    navigateToSettings: () -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_quotes),
            contentDescription = null,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(100.dp)
        )
        DrawerMenu(
            iconId = R.drawable.ic_quote,
            title = "Quotes",
            isSelected = currentRoute == BookberDestination.QUOTES_ROUTE,
            action = {
                navigateToQuotes()
                closeDrawer()
            }
        )
        DrawerMenu(
            iconId = R.drawable.ic_books,
            title = "Books",
            isSelected = currentRoute == BookberDestination.BOOKS_ROUTE,
            action = {
                navigateToBooks()
                closeDrawer()
            }
        )
        DrawerMenu(
            iconId = R.drawable.ic_category,
            title = "Category",
            isSelected = currentRoute == BookberDestination.CATEGORY_ROUTE,
            action = {
                navigateToCategory()
                closeDrawer()
            }
        )
        DrawerMenu(
            iconId = R.drawable.ic_favorite,
            title = "Favorite",
            isSelected = currentRoute == BookberDestination.FAVORITE_ROUTE,
            action = {
                navigateToFavorite()
                closeDrawer()
            }
        )
        DrawerMenu(
            iconId = R.drawable.ic_widgets,
            title = "Widget",
            isSelected = false,
            action = {
                navigateToWidget()
                closeDrawer()
            }
        )
        DrawerMenu(
            iconId = R.drawable.ic_settings,
            title = "Settings",
            isSelected = false,
            action = {
                navigateToSettings()
                closeDrawer()
            }
        )
    }
}

@Composable
private fun DrawerMenu(
    iconId: Int,
    title: String,
    isSelected: Boolean,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedText = if (isSelected) {
        Color.Black
    } else {
        Color.Black.copy(alpha = 0.4f)
    }

    val selectedBg = if (isSelected) {
        Color(0xffFEDBD0)
    } else {
        Color.White
    }

    TextButton(
        onClick = action, modifier = modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(selectedBg, RoundedCornerShape(100.dp))
                .padding(12.dp)
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = title, style = MaterialTheme.typography.bodyMedium, color = selectedText)
        }
    }
}