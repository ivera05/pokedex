package com.pokedex.controller

import com.pokedex.dto.PageResponseDto
import com.pokedex.dto.PokemonDto
import com.pokedex.dto.TrainerDto
import com.pokedex.entity.UserEntity
import com.pokedex.service.TrainerService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/trainer")
@Tag(name = "Trainer", description = "Operations related to the Trainer")
class TrainerController(
    private val trainerService: TrainerService,
) {
    private fun getAuthenticatedUserId(): Long {
        val user = SecurityContextHolder.getContext().authentication?.principal as UserEntity
        return user.id
    }

    @GetMapping("/")
    @Operation(
        summary = "Get Trainer Information",
        description = "Retrieve information about the authenticated trainer.",
    )
    fun getTrainerInfo(): ResponseEntity<TrainerDto> {
        val trainer = trainerService.findByUserId(getAuthenticatedUserId())
        return ResponseEntity.ok(TrainerDto.fromEntity(trainer))
    }

    @PostMapping("/catch/{pokemonId}")
    @Operation(summary = "Catch a Pokemon", description = "Catch a Pokemon by its ID.")
    fun catchPokemon(
        @PathVariable pokemonId: Long,
    ): ResponseEntity<String> {
        trainerService.catchPokemon(getAuthenticatedUserId(), pokemonId)
        return ResponseEntity.ok("Pokemon caught successfully")
    }

    @GetMapping("/pokemons")
    @Operation(summary = "Get Caught Pokemons", description = "Retrieve a list of caught Pokemons.")
    fun getCaughtPokemons(
        @PageableDefault(size = 20) pageable: Pageable,
    ): ResponseEntity<PageResponseDto<PokemonDto>> {
        val caughtPokemons = trainerService.getCaughtPokemons(getAuthenticatedUserId(), pageable)
        return ResponseEntity.ok(caughtPokemons)
    }
}
