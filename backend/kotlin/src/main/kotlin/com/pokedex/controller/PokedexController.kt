package com.pokedex.controller

import com.pokedex.dto.PageResponseDto
import com.pokedex.dto.PokemonDto
import com.pokedex.dto.PokemonFilter
import com.pokedex.service.PokemonService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/pokedex")
@Tag(name = "Pokedex", description = "Operations related to the Pokedex")
class PokedexController( private val pokemonService: PokemonService) {

    @GetMapping("/")
    @Operation(summary = "Get a paginated list of all Pokemon", description = "Retrieve a paginated list of all available Pokemon in the Pokedex.")
    fun getPokemonList(
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) type: String?,
        @PageableDefault(size = 20) pageable: Pageable
    ): ResponseEntity<PageResponseDto<PokemonDto>> {
        val filter = PokemonFilter(
            name = name,
            type = type
        ).takeIf { it.name != null || it.type != null } ?: PokemonFilter()
        return ResponseEntity.ok(pokemonService.getAllPokemon(filter, pageable))
    }

    @GetMapping("/{pokemonId}")
    @Operation(summary = "Get a specific Pokemon by ID", description = "Retrieve information about a specific Pokemon by its ID.")
    fun getPokemonById(@PathVariable pokemonId: Int): PokemonDto? {
        return pokemonService.getPokemonById(pokemonId)
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Get paginated Pokemon by Type", description = "Retrieve a paginated list of Pokemon of a specific type.")
    fun getPokemonByType(
        @PathVariable type: String,
        @PageableDefault(size = 20) pageable: Pageable
    ): ResponseEntity<PageResponseDto<PokemonDto>> {
        return ResponseEntity.ok(pokemonService.getPokemonByType(type, pageable))
    }
}