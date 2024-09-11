package com.example.pokedex.services

import com.example.pokedex.entities.PokemonEntity
import com.example.pokedex.repositories.PokemonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PokemonService @Autowired constructor(
    private val pokemonRepository: PokemonRepository
) {

    fun add(pokemonEntity: PokemonEntity): PokemonEntity {
        return pokemonRepository.save<PokemonEntity>(pokemonEntity)
    }

    fun findById(id: Long): PokemonEntity {
        return pokemonRepository.findById(id).orElseThrow { IllegalArgumentException("Pokemon not found") }
    }

    fun findAll(): List<PokemonEntity> {
        return pokemonRepository.findAll().sortedBy { it.id }
    }
}