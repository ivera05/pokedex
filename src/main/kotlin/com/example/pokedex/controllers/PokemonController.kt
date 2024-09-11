package com.example.pokedex.controllers

import com.example.pokedex.entities.PokemonEntity
import com.example.pokedex.services.PokemonService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class PokemonController(
    private val pokemonService: PokemonService
) {

    @GetMapping("/pokemon")
    fun findAll(): List<PokemonEntity> {
        return pokemonService.findAll()
    }

    @GetMapping("/pokemon/{id}")
    fun findById(@PathVariable id: String): PokemonEntity {
        return pokemonService.findById(id.toLong())
    }
}