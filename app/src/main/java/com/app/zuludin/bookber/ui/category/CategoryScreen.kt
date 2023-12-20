package com.app.zuludin.bookber.ui.category

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.zuludin.bookber.R
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.ui.category.components.CategoryItem
import com.app.zuludin.bookber.ui.category.components.CategoryManagementSheet
import com.app.zuludin.bookber.util.components.ConfirmAlertDialog
import com.app.zuludin.bookber.util.getViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryScreen(
    openDrawer: () -> Unit,
    viewModel: CategoryViewModel = viewModel(factory = getViewModelFactory())
) {

    val tabs = listOf("Quote", "Book")
    val pagerState = rememberPagerState { tabs.size }
    val coroutineScope = rememberCoroutineScope()

    var selectedCategoryId = ""
    var tabIndex by remember { mutableIntStateOf(0) }
    var showCategorySheet by remember { mutableStateOf(false) }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }

    val bookCategories by viewModel.bookCategories.observeAsState(initial = emptyList())
    val quoteCategories by viewModel.quoteCategories.observeAsState(initial = emptyList())


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Category") },
                backgroundColor = Color.White,
                navigationIcon = {
                    IconButton(onClick = openDrawer) {
                        Icon(Icons.Filled.Menu, contentDescription = null)
                    }
                },
                elevation = 0.dp,
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showCategorySheet = true }) {
                Icon(painter = painterResource(id = R.drawable.ic_add), contentDescription = null)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
        ) {
            TabRow(selectedTabIndex = tabIndex, containerColor = Color.White) {
                tabs.fastForEachIndexed { index, title ->
                    Tab(
                        selected = tabIndex == index,
                        onClick = {
                            tabIndex = index
                            coroutineScope.launch { pagerState.animateScrollToPage(tabIndex) }
                        },
                        modifier = Modifier.padding(12.dp),
                    ) {
                        Text(text = title)
                    }
                }
            }

            HorizontalPager(state = pagerState, modifier = Modifier.fillMaxWidth()) { index ->
                when (index) {
                    0 -> CategoryContents(
                        categories = quoteCategories,
                        onDeleteCategory = { id ->
                            selectedCategoryId = id
                            showDeleteConfirmDialog = true
                        })

                    1 -> CategoryContents(categories = bookCategories,
                        onDeleteCategory = { id ->
                            selectedCategoryId = id
                            showDeleteConfirmDialog = true
                        })
                }
            }
        }

        LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
            if (!pagerState.isScrollInProgress) {
                tabIndex = pagerState.currentPage
            }
        }

        if (showCategorySheet) {
            CategoryManagementSheet(
                type = if (tabIndex == 0) "Quote" else "Book",
                onSaveCategory = { cat ->
                    viewModel.saveNewCategory(cat)
                    showCategorySheet = false
                },
                onDismissRequest = { showCategorySheet = !showCategorySheet }
            )
        }

        if (showDeleteConfirmDialog) {
            ConfirmAlertDialog(
                message = "Are you sure want to delete this category?",
                onDismissRequest = { showDeleteConfirmDialog = false },
                onConfirm = { viewModel.deleteSelectedCategory(selectedCategoryId) }
            )
        }
    }
}

@Composable
private fun CategoryContents(categories: List<CategoryEntity>, onDeleteCategory: (String) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(categories) { cat ->
            val index = categories.indexOf(cat)
            val color = if ((index % 2) == 0) Color.Blue else Color.Green

            CategoryItem(color = color, category = cat, onDeleteCategory = onDeleteCategory)
        }
    }
}