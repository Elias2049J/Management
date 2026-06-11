package com.example.management.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun HomeScreen() {

    var selected by remember {
        mutableIntStateOf(0)
    }

    Scaffold(

        bottomBar = {

            NavigationBar {

                NavigationBarItem(
                    selected = selected == 0,
                    onClick = {
                        selected = 0
                    },
                    icon = {
                        Icon(Icons.Default.Home,null)
                    },
                    label = {
                        Text("Home")
                    }
                )

                NavigationBarItem(
                    selected = selected == 1,
                    onClick = {
                        selected = 1
                    },
                    icon = {
                        Icon(Icons.Default.Person,null)
                    },
                    label = {
                        Text("Locales")
                    }
                )
            }
        }

    ) { padding ->

        when(selected){

            0 -> UserScreen()

            1 -> {

                // LocalUsersScreen(...)
            }
        }
    }
}