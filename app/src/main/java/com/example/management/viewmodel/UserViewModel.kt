package com.example.management.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.management.data.model.User
import com.example.management.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun getUsers() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                _users.value = repository.getUsers()

            } catch (e: Exception) {
                _errorMessage.value = "Error al obtener usuarios"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
