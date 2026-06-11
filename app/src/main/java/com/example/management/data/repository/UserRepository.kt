package com.example.management.data.repository

import com.example.management.data.local.LocalUser
import com.example.management.data.model.User

interface UserRepository {

    suspend fun getUsersFromApi(): List<User>

    suspend fun registerUser(user: LocalUser)

    suspend fun update(user: LocalUser)

    suspend fun login(username: String, password: String): LocalUser?
}