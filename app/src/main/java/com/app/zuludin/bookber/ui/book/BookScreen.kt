package com.app.zuludin.bookber.ui.book

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
import com.app.zuludin.bookber.domain.model.Book
import com.app.zuludin.bookber.util.components.CategoryFilterChips
import com.app.zuludin.bookber.util.components.EmptyContentLayout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookScreen(
    openDrawer: () -> Unit,
    onAddBook: () -> Unit,
    onDetailBook: (Book) -> Unit,
    viewModel: BookViewModel = hiltViewModel(),
) {
    Scaffold(
        modifier = Modifier.shadow(elevation = 0.dp),
        topBar = {
            TopAppBar(
                title = { Text(text = "Books") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                navigationIcon = {
                    IconButton(onClick = openDrawer) {
                        Icon(Icons.Filled.Menu, contentDescription = null)
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddBook) {
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
                viewModel.filterBookByCategory(cat.id)
            })

            Spacer(modifier = Modifier.height(8.dp))

            ShowBookContent(uiState = uiState.books, onDetailBook = onDetailBook)
        }
    }
}

@Composable
private fun ShowBookContent(uiState: BooksUiState, onDetailBook: (Book) -> Unit) {
    when (uiState) {
        BooksUiState.Error -> Text(text = "Error Load Book")
        BooksUiState.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }

        is BooksUiState.Success -> {
            if (uiState.books.isEmpty()) {
                EmptyContentLayout(message = "Book is Empty")
            } else {
                LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                    items(uiState.books) { book ->
                        BookItem(book = book, onClick = { onDetailBook(book) })
                    }
                }
            }
        }
    }
}