package com.pokedex.dto

import com.pokedex.entity.UserRole

data class RegisterRequest(
    val username: String,
    val password: String,
    val role: UserRole? = null,
    val name: String,
    val avatar: String? = null,
)

data class LoginRequest(
    val username: String,
    val password: String,
)
