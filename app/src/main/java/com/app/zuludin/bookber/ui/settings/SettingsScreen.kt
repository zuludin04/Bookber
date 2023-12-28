package com.app.zuludin.bookber.ui.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.app.zuludin.bookber.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(openDrawer: () -> Unit, onShowCategory: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Settings") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                navigationIcon = {
                    IconButton(onClick = openDrawer) {
                        Icon(Icons.Filled.Menu, contentDescription = null)
                    }
                },
            )
        }
    ) {
        val context = LocalContext.current

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
                .padding(15.dp)
        ) {
            SettingsGroup(name = R.string.general) {
                SettingsClickableComp(
                    icon = R.drawable.ic_category,
                    name = R.string.category,
                    onClick = onShowCategory
                )
//                SettingsClickableComp(
//                    icon = R.drawable.ic_theme,
//                    name = R.string.theme,
//                    onClick = {}
//                )
            }

            SettingsGroup(name = R.string.about) {
                SettingsClickableComp(
                    icon = R.drawable.ic_rate,
                    name = R.string.rate,
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW)
                        val appLink =
                            "https://play.google.com/store/apps/details?id=com.app.zuludin.bookber"
                        intent.data = Uri.parse(appLink)
                        context.startActivity(intent)
                    }
                )
                SettingsClickableComp(
                    icon = R.drawable.ic_share,
                    name = R.string.share,
                    onClick = {
                        val intent = Intent(Intent.ACTION_SEND)
                        val appLink =
                            "https://play.google.com/store/apps/details?id=com.app.zuludin.bookber"
                        intent.type = "text/plain"
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Bookber")
                        intent.putExtra(
                            Intent.EXTRA_TEXT,
                            "Share Bookber with your friend $appLink"
                        )
                        context.startActivity(Intent.createChooser(intent, "Share App"))
                    }
                )
                SettingsClickableComp(
                    icon = R.drawable.ic_version,
                    name = R.string.app_version,
                    onClick = {},
                    clickable = false
                )
            }
        }
    }
}