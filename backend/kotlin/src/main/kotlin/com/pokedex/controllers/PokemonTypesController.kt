package com.pokedex.controllers

import com.pokedex.entities.PokemonTypeEntity
import com.pokedex.services.PokemonTypeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PokemonTypesController(
    private val pokemonTypeService: PokemonTypeService
) {

    @GetMapping("/api/pokemon-types")
    fun findAll(): List<PokemonTypeEntity> {
        return pokemonTypeService.findAll()
    }

    @PostMapping("/api/pokemon-types")
    fun create(pokemonTypeEntity: PokemonTypeEntity): PokemonTypeEntity {
        return pokemonTypeService.save(pokemonTypeEntity)
    }

    @PostMapping("/api/pokemon-types/sync")
    fun sync(){
        return pokemonTypeService.sync()
    }
}