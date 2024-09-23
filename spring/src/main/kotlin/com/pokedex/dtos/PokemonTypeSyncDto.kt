package com.pokedex.dtos

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class PokemonTypeSyncDto(
    val english: String,
    val effective: List<String>,
    val ineffective: List<String>,
    @JsonProperty("no_effect")
    val noEffect: List<String>
)
