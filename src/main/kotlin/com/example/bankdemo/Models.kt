package com.example.bankdemo

import java.math.BigDecimal

data class User(
    val id: Int,
    val name: String,
    val balance: BigDecimal,
    val updatedAt: String,
)

data class CreateUserRequest(
    val name: String
)
data class UpdateUserRequest(
    val id: Int,
    val name: String,
    val balance: BigDecimal,
)
