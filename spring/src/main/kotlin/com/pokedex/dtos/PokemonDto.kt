package com.pokedex.dtos

import com.fasterxml.jackson.annotation.JsonProperty

data class PokemonDto(
    val id: Long,
    val name: String,
    val type: List<String>,
    val description: String,
    val species: String,
    val base: BaseDto?,
    val evolutions: EvolutionsDto,
    val profile: ProfileDto,
    val images: ImagesDto
)

data class BaseDto(
    val hp: Int,
    val attack: Int,
    val defense: Int,
    @JsonProperty("special_attack")
    val specialAttack: Int,
    @JsonProperty("special_defense")
    val specialDefense: Int,
    val speed: Int
)

data class EvolutionsDto(
    var prev: EvolutionDto?,
    var next: List<EvolutionDto>
)

data class EvolutionDto(
    val id: Long?,
    val condition: String
)

data class ProfileDto(
    val height: String,
    val weight: String,
    val abilities: List<AbilityDto>
)

data class AbilityDto(
    val ability: String,
    val isHidden: Boolean
)

data class ImagesDto(
    val sprite: String?,
    val thumbnail: String?,
    val hires: String?
)