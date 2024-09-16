package com.pokedex.controllers

import com.pokedex.dtos.PokemonDto
import com.pokedex.services.PokemonService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PokemonController(
    private val pokemonService: PokemonService
) {

    @GetMapping("/pokemon")
    fun findAll(): List<PokemonDto> {
        return pokemonService.findAll()
    }

    @GetMapping("/pokemon/{id}")
    fun findById(@PathVariable id: String): PokemonDto {
        return pokemonService.findById(id.toLong())
    }

    @PostMapping("/pokemon/sync")
    fun sync(){
        return pokemonService.sync("https://raw.githubusercontent.com/Purukitto/pokemon-data.json/master/pokedex.json")
    }
}