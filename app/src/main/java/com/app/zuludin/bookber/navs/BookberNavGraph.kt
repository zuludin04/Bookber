package com.app.zuludin.bookber.navs

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.zuludin.bookber.navs.BookberDestinationArgs.BOOK_ID_ARG
import com.app.zuludin.bookber.navs.BookberDestinationArgs.BOOK_STATE_ARG
import com.app.zuludin.bookber.navs.BookberDestinationArgs.QUOTE_ID_ARG
import com.app.zuludin.bookber.ui.book.BookScreen
import com.app.zuludin.bookber.ui.category.CategoryScreen
import com.app.zuludin.bookber.ui.favorite.FavoriteScreen
import com.app.zuludin.bookber.ui.quote.QuoteScreen
import com.app.zuludin.bookber.ui.quotebookmgmt.QuoteBookManagementScreen
import com.app.zuludin.bookber.ui.quotedetail.QuoteDetailScreen
import com.app.zuludin.bookber.util.components.BookberModalDrawer
import com.app.zuludin.bookber.util.enums.BookInfoState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BookberNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    startDestination: String = BookberDestination.QUOTES_ROUTE,
    navActions: BookberNavigationActions = remember(navController) {
        BookberNavigationActions(navController)
    }
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(BookberDestination.QUOTES_ROUTE) {
            BookberModalDrawer(
                drawerState = drawerState,
                currentRoute = currentRoute,
                navigationActions = navActions
            ) {
                QuoteScreen(
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    onAddQuote = {
                        navActions.navigateToBookQuoteManagement(
                            BookInfoState.ADD_QUOTE.name,
                            null
                        )
                    },
                    onOpenDetailQuote = {
                        navActions.navigateToQuoteDetail(it)
                    }
                )
            }
        }

        composable(BookberDestination.BOOKS_ROUTE) {
            BookberModalDrawer(drawerState, currentRoute, navActions) {
                BookScreen(
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    onAddBook = {
                        navActions.navigateToBookQuoteManagement(
                            BookInfoState.ADD_BOOK.name,
                            null
                        )
                    },
                    onDetailBook = {
                        navActions.navigateToBookQuoteManagement(
                            BookInfoState.DETAIL_BOOK.name,
                            it.id
                        )
                    }
                )
            }
        }

        composable(BookberDestination.CATEGORY_ROUTE) {
            BookberModalDrawer(drawerState, currentRoute, navActions) {
                CategoryScreen(openDrawer = { coroutineScope.launch { drawerState.open() } })
            }
        }

        composable(
            BookberDestination.BOOK_QUOTE_MANAGEMENT_ROUTE,
            arguments = listOf(
                navArgument(BOOK_STATE_ARG) { type = NavType.StringType },
                navArgument(BOOK_ID_ARG) { type = NavType.StringType; nullable = true }
            )
        ) { entry ->
            val state = entry.arguments?.getString(BOOK_STATE_ARG)
            val bookId = entry.arguments?.getString(BOOK_ID_ARG)
            QuoteBookManagementScreen(
                onBack = { navController.popBackStack() },
                bookId = bookId,
                bookState = state!!,
                onOpenDetailQuote = {
                    navActions.navigateToQuoteDetail(it)
                }
            )
        }

        composable(BookberDestination.FAVORITE_ROUTE) {
            BookberModalDrawer(drawerState, currentRoute, navActions) {
                FavoriteScreen(openDrawer = { coroutineScope.launch { drawerState.open() } })
            }
        }

        composable(BookberDestination.QUOTE_DETAIL_ROUTE) { entry ->
            QuoteDetailScreen(
                quoteId = entry.arguments?.getString(QUOTE_ID_ARG)!!,
                onBack = { navController.popBackStack() }
            )
        }
    }
}