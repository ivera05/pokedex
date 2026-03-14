package com.pokedex.dto

import com.pokedex.entity.PokemonTypeEnum

data class PokemonFilter(
    val name: String? = null,
    val type: PokemonTypeEnum? = null,
)
