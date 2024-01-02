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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.zuludin.bookber.R
import com.app.zuludin.bookber.data.local.entity.CategoryEntity
import com.app.zuludin.bookber.util.components.ConfirmAlertDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    openDrawer: () -> Unit,
    viewModel: CategoryViewModel = hiltViewModel(),
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
                modifier = Modifier.shadow(elevation = 0.dp),
                title = { Text(text = "Category") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                navigationIcon = {
                    IconButton(onClick = openDrawer) {
                        Icon(Icons.Filled.Menu, contentDescription = null)
                    }
                },
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
            TabRow(
                selectedTabIndex = tabIndex,
                containerColor = MaterialTheme.colorScheme.background
            ) {
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
            CategoryItem(category = cat, onDeleteCategory = onDeleteCategory)
            HorizontalDivider()
        }
    }
}