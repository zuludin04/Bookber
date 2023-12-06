package com.app.zuludin.bookber.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.app.zuludin.bookber.BookberApplication

@Composable
fun getViewModelFactory(): ViewModelFactory {
    val repository = (LocalContext.current.applicationContext as BookberApplication).repository
    return ViewModelFactory(repository)
}