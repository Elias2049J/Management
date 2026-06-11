package com.example.management.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun ErrorAlertDialog(
    errorMsg: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = "Error",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.error
            )
        },
        text = {
            Text(errorMsg)
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("Confirmar")
            }
        }
    )
}
