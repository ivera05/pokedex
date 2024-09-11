package com.example.pokedex.services

import com.example.pokedex.entities.PokemonTypeEntity
import com.example.pokedex.repositories.PokemonTypeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PokemonTypeService @Autowired constructor(
    private val pokemonTypeRepository: PokemonTypeRepository
) {

    fun findAll(): List<PokemonTypeEntity> {
        return pokemonTypeRepository.findAll()
    }

    fun add(pokemonTypeEntity: PokemonTypeEntity): PokemonTypeEntity {
        return pokemonTypeRepository.save<PokemonTypeEntity>(pokemonTypeEntity)
    }
}