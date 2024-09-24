package com.pokedex.repositories

import com.pokedex.entities.PokemonAbilityEntity
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface PokemonAbilityPort {
}

class PokemonAbilityPortImpl( @PersistenceContext private val entityManager: EntityManager): PokemonAbilityPort {
}

@Repository
interface PokemonAbilityRepository: JpaRepository<PokemonAbilityEntity, Long>, PokemonAbilityPort
