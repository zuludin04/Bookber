package com.app.zuludin.bookber.util.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ConfirmAlertDialog(message: String, onDismissRequest: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        modifier = Modifier.shadow(0.dp, ambientColor = Color.Red),
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
                onDismissRequest()
            }) {
                Text(text = "Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = "No")
            }
        },
        title = {
            Text(
                text = message,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
    )
}