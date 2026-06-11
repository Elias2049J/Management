package com.example.management.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.management.data.local.LocalUser
import com.example.management.ui.screens.MainHomeScreen
import com.example.management.viewmodel.UserViewModel
import com.example.management.viewmodel.UserViewModelFactory

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(context))

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    userViewModel.resetLoginState()
                    navController.navigate("main_home") {
                        popUpTo("login") { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavigateToSignUp = {
                    userViewModel.resetLoginState()
                    userViewModel.resetRegistrationState()
                    navController.navigate("signup")
                },
                userViewModel = userViewModel
            )
        }
        composable("signup") {
            SignUpScreen(
                onSignUpSuccess = {
                    userViewModel.resetLoginState()
                    userViewModel.resetRegistrationState()
                    navController.popBackStack()
                },
                onNavigateToLogin = {
                    userViewModel.resetLoginState()
                    userViewModel.resetRegistrationState()
                    navController.popBackStack()
                },
                userViewModel = userViewModel
            )
        }
        composable("home") {
            MainHomeScreen(
                navController = navController,
                userViewModel = userViewModel
            )
        }

        composable("main_home") {
            MainHomeScreen(
                navController = navController,
                userViewModel = userViewModel
            )
        }

        composable("register_user") {
            RegisterUserScreen(
                userViewModel = userViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "edit_user/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: -1
            var localUser by remember { mutableStateOf<LocalUser?>(null) }
            var isLoading by remember { mutableStateOf(true) }
            var loadError by remember { mutableStateOf<String?>(null) }

            LaunchedEffect(userId) {
                isLoading = true
                loadError = null
                localUser = try {
                    userViewModel.getLocalUserById(userId)
                } catch (e: Exception) {
                    loadError = "No se pudo cargar el usuario"
                    null
                }
                isLoading = false
            }

            when {
                isLoading -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }

                loadError != null -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(loadError ?: "No se pudo cargar el usuario")
                }

                localUser == null -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Usuario no encontrado")
                }

                else -> EditUserScreen(
                    localUser = localUser!!,
                    userViewModel = userViewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

