package com.example.management.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: LocalUser)

    @Update
    suspend fun update(user: LocalUser)

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUserByUsername(username: String): LocalUser?

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun findById(id: Int): LocalUser?

    @Query("SELECT * FROM users")
    suspend fun findAll(): List<LocalUser>
}