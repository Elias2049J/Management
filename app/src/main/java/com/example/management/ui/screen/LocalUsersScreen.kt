package com.example.management.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.management.data.local.LocalUser

@Composable
fun LocalUsersScreen(
    users: List<LocalUser>,
    onDelete: (LocalUser) -> Unit
) {

    var selectedUser by remember {
        mutableStateOf<LocalUser?>(null)
    }

    Scaffold(

        floatingActionButton = {

            FloatingActionButton(
                onClick = {
                    // Registrar usuario
                }
            ) {
                Icon(Icons.Default.Add,null)
            }
        }

    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            items(users) { user ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement =
                            Arrangement.SpaceBetween
                    ) {

                        Column {

                            Text(user.username)

                            Text(user.email)

                        }

                        Row {

                            IconButton(
                                onClick = {

                                }
                            ) {
                                Icon(
                                    Icons.Default.Edit,
                                    null
                                )
                            }

                            IconButton(
                                onClick = {
                                    selectedUser = user
                                }
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    null
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    selectedUser?.let { user ->

        AlertDialog(

            onDismissRequest = {
                selectedUser = null
            },

            title = {
                Text("Eliminar usuario")
            },

            text = {
                Text(
                    "¿Estás seguro de eliminar este usuario?"
                )
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        onDelete(user)

                        selectedUser = null
                    }
                ) {
                    Text("Eliminar")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        selectedUser = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}