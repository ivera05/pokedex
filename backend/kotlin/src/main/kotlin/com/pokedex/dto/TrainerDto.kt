package com.pokedex.dto

data class TrainerDto(
    val id: Long,
    val email: String,
    val displayName: String,
    val title: String,
    val region: String,
    val badges: Int,
    val avatar: String,
)
