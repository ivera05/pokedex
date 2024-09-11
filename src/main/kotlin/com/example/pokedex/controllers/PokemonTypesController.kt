package com.example.pokedex.controllers

import com.example.pokedex.entities.PokemonTypeEntity
import com.example.pokedex.services.PokemonTypeService
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
        return pokemonTypeService.add(pokemonTypeEntity)
    }
}