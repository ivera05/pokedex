package com.pokedex.service

import com.pokedex.dto.PageResponseDto
import com.pokedex.dto.PokemonDto
import com.pokedex.dto.PokemonFilter
import com.pokedex.repository.PokemonRepository
import com.pokedex.utils.toPageResponse
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PokemonService(
    private val pokemonRepository: PokemonRepository,
) {
    fun getPokemonById(id: Int): PokemonDto? {
        val pokemon =
            pokemonRepository
                .findById(id.toLong())
                .orElseThrow { IllegalArgumentException("Pokemon with ID $id not found") }

        return PokemonDto.fromEntity(pokemon)
    }

    fun getAllPokemon(
        filter: PokemonFilter,
        pageable: Pageable,
    ): PageResponseDto<PokemonDto> {
        val name = filter.name?.takeIf { it.isNotBlank() }?.lowercase()
        val type = filter.type?.takeIf { true }
        return pokemonRepository
            .findAllByFilters(name, type, pageable)
            .map { PokemonDto.fromEntity(it) }
            .toPageResponse()
    }

    fun getPokemonByType(
        type: String,
        pageable: Pageable,
    ): PageResponseDto<PokemonDto> =
        pokemonRepository
            .findAllByType(type, pageable)
            .map { PokemonDto.fromEntity(it) }
            .toPageResponse()
}
