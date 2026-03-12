package com.pokedex.dto

data class PokemonDto(
    val id: Long,
    val name: String,
    val types: List<String>,
    val baseHP: Int,
    val baseAttack: Int,
    val baseDefense: Int,
    val baseSpecialAttack: Int,
    val baseSpecialDefense: Int,
    val baseSpeed: Int,
    val description: String,
    val species: String,
    val height: Float,
    val weight: Float,
    val abilities: List<String>,
    val image: String,
    val evolutions: List<EvolutionDto>,
    val previousEvolutions: List<EvolutionDto>,
)

data class EvolutionDto(
    val id: Long,
    val name: String,
    val trigger: String,
)
