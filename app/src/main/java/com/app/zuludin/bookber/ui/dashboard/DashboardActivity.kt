package com.app.zuludin.bookber.ui.dashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.app.zuludin.bookber.navs.BookberNavGraph
import com.google.accompanist.appcompattheme.AppCompatTheme

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppCompatTheme {
                BookberNavGraph()
            }
        }
    }
}