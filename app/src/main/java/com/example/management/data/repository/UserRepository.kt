package com.example.management.data.repository

import com.example.management.data.local.LocalUser
import com.example.management.data.model.User

interface UserRepository {

    suspend fun getUsers(): List<User>

    suspend fun registerUser(user: LocalUser)

    suspend fun login(username: String, password: String): LocalUser?

    suspend fun getAllLocalUsers(): List<LocalUser>

    suspend fun updateUser(user: LocalUser)

    suspend fun deleteUser(user: LocalUser)

}