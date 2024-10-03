package com.pokedex.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class AuthRequest(
    @field:NotBlank(message = "Username is required.")
    @JsonProperty("username")
    val username: String,

    @field:NotBlank(message = "Password is required.")
    @JsonProperty("password")
    val password: String
)
