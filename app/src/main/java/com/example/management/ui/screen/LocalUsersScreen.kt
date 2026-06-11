package com.example.management.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.management.data.local.LocalUser
import com.example.management.viewmodel.UserViewModel
import com.example.management.viewmodel.UserViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalUsersScreen(
    navController: NavController,
    viewModel: UserViewModel = viewModel(factory = UserViewModelFactory(LocalContext.current))
) {
    val localUsers by viewModel.localUsers.collectAsState()
    val operationState by viewModel.localOperationState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var expandedMenuUserId by remember { mutableStateOf<Int?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var userToDelete by remember { mutableStateOf<LocalUser?>(null) }

    LaunchedEffect(Unit) {
        viewModel.loadLocalUsers()
    }

    LaunchedEffect(operationState) {
        when (operationState) {
            is UserViewModel.LocalOperationState.Success -> {
                snackbarHostState.showSnackbar((operationState as UserViewModel.LocalOperationState.Success).message)
                viewModel.resetLocalOperationState()
            }
            is UserViewModel.LocalOperationState.Error -> {
                snackbarHostState.showSnackbar((operationState as UserViewModel.LocalOperationState.Error).message)
                viewModel.resetLocalOperationState()
            }
            else -> Unit
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Usuarios locales", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF6200EE)),
                actions = {
                    IconButton(onClick = { /* Búsqueda opcional */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color.White)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("register_user") },
                containerColor = Color(0xFF6200EE)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Nuevo usuario", tint = Color.White)
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(localUsers) { user ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = user.username, fontWeight = MaterialTheme.typography.titleMedium.fontWeight)
                            Text(text = user.email, color = Color.Gray)
                        }
                        Box {
                            IconButton(
                                onClick = { expandedMenuUserId = if (expandedMenuUserId == user.id) null else user.id }
                            ) {
                                Icon(Icons.Default.MoreVert, contentDescription = "Opciones")
                            }
                            DropdownMenu(
                                expanded = expandedMenuUserId == user.id,
                                onDismissRequest = { expandedMenuUserId = null }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Editar") },
                                    onClick = {
                                        expandedMenuUserId = null
                                                navController.navigate("edit_user/${user.id}")
                                    },
                                    leadingIcon = { Icon(Icons.Default.Edit, null) }
                                )
                                DropdownMenuItem(
                                    text = { Text("Eliminar") },
                                    onClick = {
                                        expandedMenuUserId = null
                                        userToDelete = user
                                        showDeleteDialog = true
                                    },
                                    leadingIcon = { Icon(Icons.Default.Delete, null) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }


    if (showDeleteDialog && userToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Eliminar usuario") },
            text = { Text("¿Estás seguro de que deseas eliminar este usuario?\nEsta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteLocalUser(userToDelete!!)
                        showDeleteDialog = false
                        userToDelete = null
                    }
                ) {
                    Text("Eliminar", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}