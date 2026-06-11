package com.example.management.data.remote

class ApiUserRepository(
    private val apiService: ApiService
) {
    suspend fun getUsers(): List<ApiUser> = apiService.getUsers()
}