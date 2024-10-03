package com.pokedex.dtos

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class PokemonSyncDto(
    val id: Long,
    val name: NameSyncDto,
    val type: List<String>,
    val base: BaseSyncDto?,
    val species: String,
    val description: String,
    val evolution: EvolutionSyncDto,
    val profile: ProfileSyncDto,
    val image: ImageSyncDto
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class NameSyncDto(
    val english: String,
    val japanese: String,
    val chinese: String,
    val french: String,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class BaseSyncDto(
    @JsonProperty("HP")
    val hp: Int,
    @JsonProperty("Attack")
    val attack: Int,
    @JsonProperty("Defense")
    val defense: Int,
    @JsonProperty("Sp. Attack")
    val specialAttack: Int,
    @JsonProperty("Sp. Defense")
    val specialDefense: Int,
    @JsonProperty("Speed")
    val speed: Int
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class EvolutionSyncDto(
    val prev: List<String>? = null,
    val next: List<List<String>>? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ProfileSyncDto(
    val height: String,
    val weight: String,
    val ability: List<List<String>>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ImageSyncDto(
    val sprite: String?,
    val thumbnail: String?,
    val hires: String?,
)