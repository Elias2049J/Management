package com.example.management.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.management.data.local.LocalUser
import com.example.management.ui.theme.Purple40
import com.example.management.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterUserScreen(
    userViewModel: UserViewModel,
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPass by remember { mutableStateOf(false) }
    val operationState by userViewModel.localOperationState.collectAsState()

    LaunchedEffect(operationState) {
        if (operationState is UserViewModel.LocalOperationState.Success) {
            onBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nuevo usuario", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Purple40
                ),
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre completo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Usuario") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val iconText = if (showPass) "Ocultar" else "Mostrar"
                    TextButton(onClick = { showPass = !showPass }) {
                        Text(iconText)
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val newUser = LocalUser(
                        name = name,
                        username = username,
                        email = email,
                        password = password
                    )
                    userViewModel.registerLocalUser(newUser)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40
                )
            ) {
                Text("Guardar", color = Color.White)
            }

            when (operationState) {
                is UserViewModel.LocalOperationState.Loading -> {
                    Spacer(modifier = Modifier.height(16.dp))
                    CircularProgressIndicator()
                }

                is UserViewModel.LocalOperationState.Error -> {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = (operationState as UserViewModel.LocalOperationState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                else -> Unit
            }
        }
    }
}
