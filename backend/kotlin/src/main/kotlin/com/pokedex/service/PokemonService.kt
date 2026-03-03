package com.pokedex.service

import com.pokedex.dto.PageResponseDto
import com.pokedex.dto.PokemonDto
import com.pokedex.repository.PokemonRepository
import com.pokedex.utils.toPageResponse
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PokemonService(
    private val pokemonRepository: PokemonRepository
) {
    fun getPokemonById(id: Int): PokemonDto? {
        val pokemon = pokemonRepository.findById(id.toLong())
            .orElseThrow { IllegalArgumentException("Pokemon with ID $id not found") }

        return pokemon.toDto()
    }

    fun getAllPokemon(pageable: Pageable): PageResponseDto<PokemonDto> {
        return pokemonRepository
            .findAll(pageable)
            .map { it.toDto() }
            .toPageResponse()
    }

    fun getPokemonByType(type: String, pageable: Pageable): PageResponseDto<PokemonDto> {
        return pokemonRepository
            .findAllByType(type, pageable)
            .map { it.toDto() }
            .toPageResponse()
    }
}