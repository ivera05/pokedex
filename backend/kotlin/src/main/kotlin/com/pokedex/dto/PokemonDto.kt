package com.pokedex.dto

import com.pokedex.entity.PokemonEntity
import com.pokedex.entity.PokemonEvolutionEntity

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
) {
    companion object {
        fun fromEntity(pokemon: PokemonEntity): PokemonDto =
            PokemonDto(
                id = pokemon.id,
                name = pokemon.name,
                types = pokemon.types,
                baseHP = pokemon.baseHP,
                baseAttack = pokemon.baseAttack,
                baseDefense = pokemon.baseDefense,
                baseSpecialAttack = pokemon.baseSpecialAttack,
                baseSpecialDefense = pokemon.baseSpecialDefense,
                baseSpeed = pokemon.baseSpeed,
                description = pokemon.description,
                species = pokemon.species,
                height = pokemon.height,
                weight = pokemon.weight,
                abilities = pokemon.abilities,
                image = pokemon.image,
                evolutions = pokemon.evolutions.map { EvolutionDto.fromEntity(it) },
                previousEvolutions = pokemon.previousEvolutions.map { EvolutionDto.fromEntity(it) },
            )
    }
}

data class EvolutionDto(
    val id: Long,
    val name: String,
    val trigger: String,
) {
    companion object {
        fun fromEntity(entity: PokemonEvolutionEntity) =
            EvolutionDto(
                id = entity.toPokemon.id,
                name = entity.toPokemon.name,
                trigger = entity.trigger,
            )
    }
}
