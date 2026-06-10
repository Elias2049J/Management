package com.example.management.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.management.data.local.AppDatabase
import com.example.management.data.remote.RetrofitClient
import com.example.management.data.repository.UserRepositoryImpl

class UserViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            val userDao = AppDatabase.getDatabase(context).userDao()
            val apiService = RetrofitClient.apiService
            val repository = UserRepositoryImpl(apiService, userDao)
            return UserViewModel(repository) as T
        }

        throw IllegalArgumentException("ViewModel desconocido")
    }
}