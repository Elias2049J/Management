package com.example.management.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.management.data.local.LocalUser
import com.example.management.ui.components.ErrorAlertDialog
import com.example.management.viewmodel.RegistrationState
import com.example.management.viewmodel.UserViewModel

@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    userViewModel: UserViewModel
) {
    val registrationState by userViewModel.registrationState.collectAsState()

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showAlertDialog by remember {mutableStateOf(false)}

    LaunchedEffect(registrationState) {
        if (registrationState is RegistrationState.Success) {
            onSignUpSuccess()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Crear cuenta")

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuario") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (password == confirmPassword) {
                val user = LocalUser(
                    name = "",
                    username = username,
                    email = email,
                    password = password
                )
                userViewModel.registerUser(user)
            } else {
                showAlertDialog = true
            }
        }) {
            Text("Registrarme")
        }

        when (registrationState) {
            is RegistrationState.Loading -> CircularProgressIndicator()
            is RegistrationState.Error -> {
                Text((registrationState as RegistrationState.Error).message)
            }
            else -> {}
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onNavigateToLogin) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }

    if (showAlertDialog) {
        ErrorAlertDialog(
            errorMsg = "Las contraseñas no coinciden",
            onConfirm = { showAlertDialog = false },
            onDismiss = { showAlertDialog = false }
        )
    }
}