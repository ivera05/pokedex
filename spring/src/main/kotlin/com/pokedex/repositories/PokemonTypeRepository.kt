package com.pokedex.repositories

import com.pokedex.entities.PokemonTypeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PokemonTypeRepository: JpaRepository<PokemonTypeEntity, Long> {
    fun findByType(type: String): PokemonTypeEntity
}