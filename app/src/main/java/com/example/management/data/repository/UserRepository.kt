package com.example.management.data.repository
import com.example.management.data.model.User

interface UserRepository {

    suspend fun getUsers(): List<User>
}
