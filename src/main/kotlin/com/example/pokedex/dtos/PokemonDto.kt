package com.example.pokedex.dtos

data class PokemonDto(
    val id: Long,
    var name: String,
    val type: List<String>,
    var base: BaseDto,
    var species: String,
    var description: String,
    var evolution: EvolutionDto,
    var profile: ProfileDto,
    var image: ImageDto
)

data class BaseDto(
    var hp: Int,
    var attack: Int,
    var defense: Int,
    var specialAttack: Int,
    var specialDefense: Int,
    var speed: Int
)

data class EvolutionDto(
    val prev: List<String>? = null,
    val next: List<String>? = null
)

data class ProfileDto(
    val height: String,
    val weight: String,
    val ability: AbilityDto
)

data class AbilityDto(
    val ability: String,
    val isHidden: Boolean,
)

data class ImageDto(
    val sprite: String,
    val thumbnail: String,
    val hires: String,
)