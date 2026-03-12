package com.pokedex.repository

import com.pokedex.entity.CaughtPokemonEntity
import com.pokedex.entity.PokemonEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface CaughtPokemonRepository : JpaRepository<CaughtPokemonEntity, Long> {
    @Modifying
    @Transactional
    @Query(
        value = "INSERT INTO caught_pokemons (trainer_id, pokemon_id) VALUES (:trainerId, :pokemonId)",
        nativeQuery = true,
    )
    fun catchPokemon(
        @Param("trainerId") trainerId: Long,
        @Param("pokemonId") pokemonId: Long,
    ): Int

    @Query(
        value = "SELECT cp.pokemon FROM CaughtPokemonEntity cp WHERE cp.trainer.id = :trainerId",
    )
    fun findCaughtPokemons(
        @Param("trainerId") trainerId: Long,
        pageable: Pageable,
    ): Page<PokemonEntity>
}
