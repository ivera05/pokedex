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

    @GetMapping("/api/pokemon")
    fun findAll( @RequestParam cursor: Int?, @RequestParam limit: Int? ): List<PokemonDto> {
        return service.findAll(cursor = cursor, limit = limit)
    }

    @GetMapping("/api/pokemon/{id}")
    fun findById( @PathVariable id: String ): PokemonDto {
        return service.findById(id.toLong())
    }

    @GetMapping("/api/pokemon/top_hp")
    fun findTopByHp( @RequestParam limit: Int? ): List<PokemonDto> {
        return service.findTopByHp( limit = limit )
    }

    @GetMapping("/api/pokemon/top_attack")
    fun findTopByAttack( @RequestParam limit: Int? ): List<PokemonDto> {
        return service.findTopByAttack( limit = limit )
    }

    @GetMapping("/api/pokemon/top_defense")
    fun findTopByDefense( @RequestParam limit: Int? ): List<PokemonDto> {
        return service.findTopByDefense( limit = limit )
    }

    @GetMapping("/api/pokemon/top_special_attack")
    fun findTopBySpecialAttack( @RequestParam limit: Int? ): List<PokemonDto> {
        return service.findTopBySpecialAttack( limit = limit )
    }

    @GetMapping("/api/pokemon/top_special_defense")
    fun findTopBySpecialDefense( @RequestParam limit: Int? ): List<PokemonDto> {
        return service.findTopBySpecialDefense( limit = limit )
    }

    @GetMapping("/api/pokemon/top_speed")
    fun findTopBySpeed( @RequestParam limit: Int? ): List<PokemonDto> {
        return service.findTopBySpeed( limit = limit )
    }

    @PostMapping("/api/pokemon/sync")
    fun sync(){
        return service.sync()
    }
}