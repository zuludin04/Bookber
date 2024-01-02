package com.app.zuludin.bookber.ui.quote

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.zuludin.bookber.R
import com.app.zuludin.bookber.util.components.CategoryFilterChips
import com.app.zuludin.bookber.util.components.EmptyContentLayout
import com.app.zuludin.bookber.util.components.QuoteItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuoteScreen(
    openDrawer: () -> Unit,
    onAddQuote: () -> Unit,
    onOpenDetailQuote: (String) -> Unit,
    viewModel: QuoteViewModel = hiltViewModel(),
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Quotes") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                navigationIcon = {
                    IconButton(onClick = openDrawer) {
                        Icon(Icons.Filled.Menu, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Search, contentDescription = null)
                    }
                },
                modifier = Modifier.shadow(elevation = 0.dp),
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddQuote) {
                Icon(painter = painterResource(id = R.drawable.ic_add), contentDescription = null)
            }
        }
    ) {
        val uiState by viewModel.uiState.collectAsState()

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            CategoryFilterChips(categories = uiState.categories, onFilterQuote = { cat ->
                viewModel.filterQuoteByCategory(cat.id)
            })

            Spacer(modifier = Modifier.height(8.dp))

            ShowQuoteContent(uiState = uiState.quotes, onOpenDetailQuote = onOpenDetailQuote)
        }
    }
}

@Composable
private fun ShowQuoteContent(uiState: QuotesUiState, onOpenDetailQuote: (String) -> Unit) {
    when (uiState) {
        QuotesUiState.Error -> Text(text = "Error Load Quote")
        QuotesUiState.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }

        is QuotesUiState.Success -> {
            if (uiState.quotes.isEmpty()) {
                EmptyContentLayout(message = "Quote is Empty")
            } else {
                LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                    items(uiState.quotes) { quote ->
                        QuoteItem(quote = quote, onDetailQuote = onOpenDetailQuote)
                    }
                }
            }
        }
    }
}