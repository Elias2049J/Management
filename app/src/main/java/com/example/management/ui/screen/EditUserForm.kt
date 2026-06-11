package com.example.management.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
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
fun EditUserScreen(
    localUser: LocalUser,
    userViewModel: UserViewModel,
    onBack: () -> Unit,
    onDelete: () -> Unit
) {
    var name by remember { mutableStateOf(localUser.username) }
    var username by remember { mutableStateOf(localUser.username) }
    var email by remember { mutableStateOf(localUser.email) }
    var password by remember { mutableStateOf(localUser.password) }
    var showPass by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar usuario", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Purple40
                ),
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        val updatedUser = LocalUser(
                            name = name,
                            username = username,
                            email = email,
                            password = password
                        )
                        userViewModel.update(updatedUser)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Purple40
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(color = Color.White, text = "Actualizar")
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = { onDelete() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Eliminar usuario")
                }
            }
        }
    }
}
