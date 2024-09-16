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

    @GetMapping("/pokemon-types")
    fun findAll(): List<PokemonTypeEntity> {
        return pokemonTypeService.findAll()
    }

    @PostMapping("/pokemon-types")
    fun create(pokemonTypeEntity: PokemonTypeEntity): PokemonTypeEntity {
        return pokemonTypeService.save(pokemonTypeEntity)
    }

    @PostMapping("/pokemon-types/sync")
    fun sync(){
        return pokemonTypeService.sync("https://raw.githubusercontent.com/Purukitto/pokemon-data.json/master/types.json")
    }
}