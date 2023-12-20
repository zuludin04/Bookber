package com.app.zuludin.bookber.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.zuludin.bookber.R

@Composable
fun SettingsScreen(openDrawer: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Settings") },
                backgroundColor = Color.White,
                navigationIcon = {
                    IconButton(onClick = openDrawer) {
                        Icon(Icons.Filled.Menu, contentDescription = null)
                    }
                },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
                .padding(15.dp)
        ) {
            SettingsGroup(name = R.string.general) {
                SettingsClickableComp(icon = R.drawable.ic_category, name = R.string.category) {
                    
                }
                SettingsSwitchComp(icon = R.drawable.ic_quote,  name = R.string.theme, state = false) {
                    
                }
            }

            SettingsGroup(name = R.string.about) {
                SettingsClickableComp(icon = R.drawable.ic_favorite,  name = R.string.rate) {

                }
                SettingsClickableComp(icon = R.drawable.ic_share,  name = R.string.share) {

                }
                SettingsClickableComp(icon = R.drawable.ic_remove,  name = R.string.app_version) {

                }
            }
        }
    }
}