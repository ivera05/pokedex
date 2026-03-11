package com.pokedex.service

import com.pokedex.dto.PageResponseDto
import com.pokedex.dto.PokemonDto
import com.pokedex.entity.TrainerEntity
import com.pokedex.entity.UserEntity
import com.pokedex.repository.CaughtPokemonRepository
import com.pokedex.repository.TrainerRepository
import com.pokedex.utils.toPageResponse
import jakarta.transaction.Transactional
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TrainerService(
    private val caughtPokemonRepository: CaughtPokemonRepository,
    private val trainerRepository: TrainerRepository
) {

    @Transactional
    fun create(title: String, user: UserEntity): TrainerEntity {
        return trainerRepository.save(TrainerEntity(user = user, title = title))
    }

    fun findByUserId(userId: Long): TrainerEntity? {
        return trainerRepository.findByUserId(userId)
    }

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