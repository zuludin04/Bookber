package com.app.zuludin.bookber.navs

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.app.zuludin.bookber.navs.BookberDestinationArgs.BOOK_ID_ARG
import com.app.zuludin.bookber.navs.BookberDestinationArgs.BOOK_STATE_ARG
import com.app.zuludin.bookber.navs.BookberDestinationArgs.QUOTE_ID_ARG

private object BookberScreens {
    const val QUOTES_SCREEN = "quotes"
    const val QUOTE_DETAIL_SCREEN = "quote_detail"
    const val BOOKS_SCREEN = "books"
    const val BOOK_QUOTE_MANAGEMENT = "bookQuoteManagement"
    const val CATEGORY_SCREEN = "category"
    const val FAVORITE_SCREEN = "favorite"
    const val SETTING_SCREEN = "setting"
}

object BookberDestinationArgs {
    const val BOOK_STATE_ARG = "bookStateArg"
    const val BOOK_ID_ARG = "bookIdArg"
    const val QUOTE_ID_ARG = "quoteIdArg"
}

object BookberDestination {
    const val QUOTES_ROUTE = BookberScreens.QUOTES_SCREEN
    const val BOOKS_ROUTE = BookberScreens.BOOKS_SCREEN
    const val BOOK_QUOTE_MANAGEMENT_ROUTE =
        "${BookberScreens.BOOK_QUOTE_MANAGEMENT}/{${BOOK_STATE_ARG}}?$BOOK_ID_ARG={$BOOK_ID_ARG}"
    const val CATEGORY_ROUTE = BookberScreens.CATEGORY_SCREEN
    const val FAVORITE_ROUTE = BookberScreens.FAVORITE_SCREEN
    const val QUOTE_DETAIL_ROUTE = "${BookberScreens.QUOTE_DETAIL_SCREEN}/{${QUOTE_ID_ARG}}"
    const val SETTING_ROUTE = BookberScreens.SETTING_SCREEN
}

class BookberNavigationActions(private val navController: NavHostController) {
    fun navigateToQuotes() {
        navController.navigate(BookberDestination.QUOTES_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
        }
    }

    fun navigateToBooks() {
        navController.navigate(BookberDestination.BOOKS_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
        }
    }

    fun navigateToBookQuoteManagement(state: String, bookId: String?) {
        navController.navigate("${BookberScreens.BOOK_QUOTE_MANAGEMENT}/$state".let {
            if (bookId != null) "$it?$BOOK_ID_ARG=$bookId" else it
        })
    }

    fun navigateToCategory() {
        navController.navigate(BookberDestination.CATEGORY_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
        }
    }

    fun navigateToFavorite() {
        navController.navigate(BookberDestination.FAVORITE_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
        }
    }

    fun navigateToSetting() {
        navController.navigate(BookberDestination.SETTING_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
        }
    }

    fun navigateToQuoteDetail(quoteId: String) {
        navController.navigate("${BookberScreens.QUOTE_DETAIL_SCREEN}/$quoteId")
    }
}