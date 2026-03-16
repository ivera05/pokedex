package com.pokedex.service

import com.pokedex.dto.PageResponseDto
import com.pokedex.dto.PokemonDto
import com.pokedex.entity.RegionEnum
import com.pokedex.entity.TrainerEntity
import com.pokedex.entity.UserEntity
import com.pokedex.repository.CaughtPokemonRepository
import com.pokedex.repository.TrainerRepository
import com.pokedex.utils.toPageResponse
import jakarta.transaction.Transactional
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TrainerService(
    private val caughtPokemonRepository: CaughtPokemonRepository,
    private val trainerRepository: TrainerRepository,
) {
    @Transactional
    fun create(
        title: String,
        bio: String,
        city: String,
        region: RegionEnum,
        user: UserEntity,
    ): TrainerEntity =
        trainerRepository.save(
            TrainerEntity(
                user = user,
                title = title,
                bio = bio,
                city = city,
                region = region,
                pokemons = mutableListOf(),
                badges = mutableListOf(),
            ),
        )

    @Cacheable(value = ["trainer"], key = "#userId", unless = "#result == null")
    fun findByUserId(userId: Long): TrainerEntity =
        trainerRepository.findByUserId(userId)
            ?: throw ChangeSetPersister.NotFoundException()

    @Transactional
    fun catchPokemon(
        trainerId: Long,
        pokemonId: Long,
    ) {
        caughtPokemonRepository.catchPokemon(trainerId, pokemonId)
    }

    @Cacheable(
        value = ["trainer-pokemon"],
        key = "{ #trainerId, #pageable.pageNumber, #pageable.pageSize }",
        unless = "#result == null",
    )
    fun getCaughtPokemons(
        trainerId: Long,
        pageable: Pageable,
    ): PageResponseDto<PokemonDto> =
        caughtPokemonRepository
            .findCaughtPokemons(trainerId, pageable)
            .map { PokemonDto.fromEntity(it) }
            .toPageResponse()
}
