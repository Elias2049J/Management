package com.example.management.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("phone")
    val phone: String = "",

    @SerializedName("address")
    val address: Address = Address(),

    @SerializedName("company")
    val company: Company = Company()
)

data class Address(
    @SerializedName("city")
    val city: String = ""
)

data class Company(
    @SerializedName("name")
    val name: String = ""
)
