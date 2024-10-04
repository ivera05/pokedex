package com.pokedex.dtos

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthResponse(
    val username: String,

    @JsonProperty("access_token")
    val accessToken: String,

    @JsonProperty("refresh_token")
    val refreshToken: String,
)
