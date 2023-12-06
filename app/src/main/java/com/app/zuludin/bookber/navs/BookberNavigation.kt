package com.app.zuludin.bookber.navs

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

private object BookberScreens {
    const val QUOTES_SCREEN = "quotes"
    const val BOOKS_SCREEN = "books"
    const val BOOK_QUOTE_MANAGEMENT = "bookQuoteManagement"
    const val CATEGORY_SCREEN = "category"
    const val FAVORITE_SCREEN = "favorite"
}

object BookberDestination {
    const val QUOTES_ROUTE = BookberScreens.QUOTES_SCREEN
    const val BOOKS_ROUTE = BookberScreens.BOOKS_SCREEN
    const val BOOK_QUOTE_MANAGEMENT_ROUTE = BookberScreens.BOOK_QUOTE_MANAGEMENT
    const val CATEGORY_ROUTE = BookberScreens.CATEGORY_SCREEN
    const val FAVORITE_ROUTE = BookberScreens.FAVORITE_SCREEN
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

    fun navigateToBookQuoteManagement() {
        navController.navigate(BookberDestination.BOOK_QUOTE_MANAGEMENT_ROUTE)
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
}