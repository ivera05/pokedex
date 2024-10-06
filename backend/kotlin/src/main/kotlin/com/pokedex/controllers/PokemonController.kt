package com.pokedex.controllers

import com.pokedex.dtos.PokemonDto
import com.pokedex.services.PokemonService
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
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
    fun findAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "15") size: Int
    ): ResponseEntity<Page<PokemonDto>> {
        val result = service.findAll(page = page, size = size)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/api/pokemon/search")
    fun search(
        @RequestParam query: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "15") size: Int
    ): ResponseEntity<Page<PokemonDto>> {
        val result = service.search(query = query, page = page, size = size)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/api/pokemon/{id}")
    fun findById( @PathVariable id: String ): ResponseEntity<PokemonDto> {
        return ResponseEntity.ok(service.findById(id.toLong()))
    }

    @GetMapping("/api/pokemon/top_hp")
    fun findTopByHp( @RequestParam(defaultValue = "15") size: Int ): ResponseEntity<List<PokemonDto>> {
        return ResponseEntity.ok(service.findTopByHp(size = size))
    }

    @GetMapping("/api/pokemon/top_attack")
    fun findTopByAttack( @RequestParam(defaultValue = "15") size: Int ): ResponseEntity<List<PokemonDto>> {
        return ResponseEntity.ok(service.findTopByAttack(size = size))
    }

    @GetMapping("/api/pokemon/top_defense")
    fun findTopByDefense( @RequestParam(defaultValue = "15") size: Int ): ResponseEntity<List<PokemonDto>> {
        return ResponseEntity.ok(service.findTopByDefense(size = size))
    }

    @GetMapping("/api/pokemon/top_special_attack")
    fun findTopBySpecialAttack( @RequestParam(defaultValue = "15") size: Int ): ResponseEntity<List<PokemonDto>> {
        return ResponseEntity.ok(service.findTopBySpecialAttack(size = size))
    }

    @GetMapping("/api/pokemon/top_special_defense")
    fun findTopBySpecialDefense( @RequestParam(defaultValue = "15") size: Int ): ResponseEntity<List<PokemonDto>> {
        return ResponseEntity.ok(service.findTopBySpecialDefense(size = size))
    }

    @GetMapping("/api/pokemon/top_speed")
    fun findTopBySpeed( @RequestParam(defaultValue = "15") size: Int ): ResponseEntity<List<PokemonDto>> {
        return ResponseEntity.ok(service.findTopBySpeed(size = size))
    }

    @PostMapping("/api/pokemon/sync")
    fun sync(): ResponseEntity<Void>{
        service.sync()
        return ResponseEntity.ok().build()
    }
}