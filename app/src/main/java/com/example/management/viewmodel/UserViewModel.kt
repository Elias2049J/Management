package com.example.management.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.management.data.local.LocalUser
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

    private val _registrationState = MutableStateFlow<RegistrationState>(RegistrationState.Idle)
    val registrationState: StateFlow<RegistrationState> = _registrationState

    private val _updateState = MutableStateFlow<UpdateState>(UpdateState.Idle)
    val updateState: StateFlow<UpdateState> = _updateState

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    private val _localUsers =
        MutableStateFlow<List<LocalUser>>(emptyList())

    val localUsers: StateFlow<List<LocalUser>>
        get() = _localUsers

    private val _localOperationState = MutableStateFlow<LocalOperationState>(LocalOperationState.Idle)
    val localOperationState: StateFlow<LocalOperationState> = _localOperationState

    sealed class LocalOperationState {
        object Idle : LocalOperationState()
        object Loading : LocalOperationState()
        data class Success(val message: String) : LocalOperationState()
        data class Error(val message: String) : LocalOperationState()
    }

    fun resetLoginState() {
        _loginState.value = LoginState.Idle
    }

    fun resetRegistrationState() {
        _registrationState.value = RegistrationState.Idle
    }

    fun resetLocalOperationState() {
        _localOperationState.value = LocalOperationState.Idle
    }

    fun getUsers() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null
                _users.value = repository.getUsersFromApi()
            } catch (e: Exception) {
                _errorMessage.value = "Error al obtener usuarios"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun registerUser(user: LocalUser) {
        viewModelScope.launch {
            try {
                _registrationState.value = RegistrationState.Loading
                repository.registerUser(user)
                runCatching { _localUsers.value = repository.getAllLocalUsers() }
                _registrationState.value = RegistrationState.Success
            } catch (e: Exception) {
                _registrationState.value = RegistrationState.Error("Error al registrar usuario")
            }
        }
    }

    fun registerLocalUser(user: LocalUser) {
        viewModelScope.launch {
            try {
                _localOperationState.value = LocalOperationState.Loading
                repository.registerUser(user)
                runCatching { _localUsers.value = repository.getAllLocalUsers() }
                _localOperationState.value = LocalOperationState.Success("Usuario guardado correctamente")
            } catch (e: Exception) {
                _localOperationState.value = LocalOperationState.Error("Error al registrar usuario")
            }
        }
    }

    fun update(user: LocalUser) {
        updateLocalUser(user)
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                _loginState.value = LoginState.Loading
                val user = repository.login(username, password)
                if (user != null) {
                    _loginState.value = LoginState.Success
                } else {
                    _loginState.value = LoginState.Error("Usuario o contraseña incorrectos")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Error al iniciar sesión")
            }
        }
    }

    suspend fun getLocalUserById(id: Int): LocalUser? {
        return repository.getLocalUserById(id)
    }

    fun loadLocalUsers() {

        viewModelScope.launch {

            try {
                _localUsers.value = repository.getAllLocalUsers()
            } catch (e: Exception) {
                _localOperationState.value = LocalOperationState.Error("Error al cargar usuarios locales")
            }
        }
    }

    fun deleteLocalUser(
        user: LocalUser
    ) {

        viewModelScope.launch {
            try {
                _localOperationState.value = LocalOperationState.Loading
                repository.deleteUser(user)
                runCatching { _localUsers.value = repository.getAllLocalUsers() }
                _localOperationState.value = LocalOperationState.Success("Usuario eliminado correctamente")
            } catch (e: Exception) {
                _localOperationState.value = LocalOperationState.Error("Error al eliminar usuario")
            }
        }
    }

    fun updateLocalUser(
        user: LocalUser
    ) {

        viewModelScope.launch {
            try {
                _localOperationState.value = LocalOperationState.Loading
                repository.updateUser(user)
                runCatching { _localUsers.value = repository.getAllLocalUsers() }
                _localOperationState.value = LocalOperationState.Success("Usuario actualizado correctamente")
            } catch (e: Exception) {
                _localOperationState.value = LocalOperationState.Error("Error al actualizar usuario")
            }
        }
    }
}

sealed class RegistrationState {
    object Idle : RegistrationState()
    object Loading : RegistrationState()
    object Success : RegistrationState()
    data class Error(val message: String) : RegistrationState()
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}

sealed class UpdateState {
    object Idle : UpdateState()
    object Loading : UpdateState()
    object Success : UpdateState()
    data class Error(val message: String) : UpdateState()
}