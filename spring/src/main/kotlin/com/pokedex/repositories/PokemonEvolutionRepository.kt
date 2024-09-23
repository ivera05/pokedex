package com.pokedex.repositories

import com.pokedex.entities.PokemonEvolutionEntity
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface PokemonEvolutionPort {
}

class PokemonEvolutionPortImpl( @PersistenceContext private val entityManager: EntityManager): PokemonEvolutionPort {
}

@Repository
interface PokemonEvolutionRepository: JpaRepository<PokemonEvolutionEntity, Long>, PokemonEvolutionPort
