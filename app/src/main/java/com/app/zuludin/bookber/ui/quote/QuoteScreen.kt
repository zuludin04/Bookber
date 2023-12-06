package com.app.zuludin.bookber.ui.quote

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.zuludin.bookber.R
import com.app.zuludin.bookber.ui.quote.components.QuoteItem
import com.app.zuludin.bookber.util.components.CategoryFilterChips
import com.app.zuludin.bookber.util.getViewModelFactory

@Composable
fun QuoteScreen(
    openDrawer: () -> Unit,
    onAddQuote: () -> Unit,
    viewModel: QuoteViewModel = viewModel(factory = getViewModelFactory())
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                backgroundColor = Color.White,
                navigationIcon = {
                    IconButton(onClick = openDrawer) {
                        Icon(Icons.Filled.Menu, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Search, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddQuote) {
                Icon(painter = painterResource(id = R.drawable.ic_add), contentDescription = null)
            }
        }
    ) {
        val categories by viewModel.categories.observeAsState(initial = emptyList())
        val quotes by viewModel.quotes.observeAsState(initial = emptyList())

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            CategoryFilterChips(categories = categories)

            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(quotes) { quote ->
                    QuoteItem(quote = quote, onDeleteQuote = {}, onRemoveFromBook = {})
                }
            }
        }
    }
}