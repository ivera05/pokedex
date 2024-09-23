package com.pokedex.controllers

import com.pokedex.dtos.PokemonDto
import com.pokedex.services.PokemonService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PokemonController(
    private val service: PokemonService
) {

    @GetMapping("/pokemon")
    fun findAll( @RequestParam cursor: Int?, @RequestParam limit: Int? ): List<PokemonDto> {
        return service.findAll(cursor = cursor, limit = limit)
    }

    @GetMapping("/pokemon/{id}")
    fun findById( @PathVariable id: String ): PokemonDto {
        return service.findById(id.toLong())
    }

    @GetMapping("/pokemon/top_hp")
    fun findTopByHp( @RequestParam limit: Int? ): List<PokemonDto> {
        return service.findTopByHp( limit = limit )
    }

    @GetMapping("/pokemon/top_attack")
    fun findTopByAttack( @RequestParam limit: Int? ): List<PokemonDto> {
        return service.findTopByAttack( limit = limit )
    }

    @GetMapping("/pokemon/top_defense")
    fun findTopByDefense( @RequestParam limit: Int? ): List<PokemonDto> {
        return service.findTopByDefense( limit = limit )
    }

    @GetMapping("/pokemon/top_special_attack")
    fun findTopBySpecialAttack( @RequestParam limit: Int? ): List<PokemonDto> {
        return service.findTopBySpecialAttack( limit = limit )
    }

    @GetMapping("/pokemon/top_special_defense")
    fun findTopBySpecialDefense( @RequestParam limit: Int? ): List<PokemonDto> {
        return service.findTopBySpecialDefense( limit = limit )
    }

    @GetMapping("/pokemon/top_speed")
    fun findTopBySpeed( @RequestParam limit: Int? ): List<PokemonDto> {
        return service.findTopBySpeed( limit = limit )
    }

    @PostMapping("/pokemon/sync")
    fun sync(){
        return service.sync("https://raw.githubusercontent.com/Purukitto/pokemon-data.json/master/pokedex.json")
    }
}