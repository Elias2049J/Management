package com.example.management.data.repository
import com.example.management.data.model.User
import com.example.management.data.remote.ApiService

class UserRepositoryImpl(
    private val apiService: ApiService
) : UserRepository {

    override suspend fun getUsers(): List<User> {
        return apiService.getUsers()
    }
}
