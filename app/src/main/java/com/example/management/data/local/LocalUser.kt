package com.example.management.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class LocalUser(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val username: String,
    val email: String,
    val password: String
)