package com.example.management.data.repository

import com.example.management.data.local.LocalUser
import com.example.management.data.local.UserDao
import com.example.management.data.model.User
import com.example.management.data.remote.ApiService

class UserRepositoryImpl(
    private val apiService: ApiService,
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getUsers(): List<User> {
        return apiService.getUsers()
    }

    override suspend fun registerUser(user: LocalUser) {
        userDao.insertUser(user)
    }

    override suspend fun login(username: String, password: String): LocalUser? {
        val user = userDao.getUserByUsername(username)
        if (user != null && user.password == password) {
            return user
        }
        return null
    }

    override suspend fun getAllLocalUsers(): List<LocalUser> {
        return userDao.getAllUsers()
    }

    override suspend fun updateUser(user: LocalUser) {
        userDao.updateUser(user)
    }

    override suspend fun deleteUser(user: LocalUser) {
        userDao.deleteUser(user)
    }
}