package com.pokedex.service

import com.pokedex.dto.PageResponseDto
import com.pokedex.dto.PokemonDto
import com.pokedex.repository.CaughtPokemonRepository
import com.pokedex.utils.toPageResponse
import jakarta.transaction.Transactional
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TrainerService(
    private val caughtPokemonRepository: CaughtPokemonRepository
) {

    @Transactional
    fun catchPokemon(trainerId: Long, pokemonId: Long) {
        caughtPokemonRepository.catchPokemon(trainerId, pokemonId)
    }

    fun getCaughtPokemons(trainerId: Long, pageable: Pageable): PageResponseDto<PokemonDto> {
        return caughtPokemonRepository
            .findCaughtPokemons(trainerId, pageable)
            .map { it.toDto() }
            .toPageResponse()
    }
}