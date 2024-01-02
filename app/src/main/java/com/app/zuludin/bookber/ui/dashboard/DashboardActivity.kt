package com.app.zuludin.bookber.ui.dashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.app.zuludin.bookber.navs.BookberNavGraph
import com.app.zuludin.bookber.theme.BookberTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookberTheme {
                BookberNavGraph()
            }
        }
    }
}