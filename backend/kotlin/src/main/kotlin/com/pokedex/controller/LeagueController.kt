package com.pokedex.controller

import com.pokedex.dto.LeagueGymsDto
import com.pokedex.service.GymService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/league")
@Tag(name = "League", description = "Operations related to the League, Region and Gyms")
class LeagueController(
    private val gymService: GymService,
) {
    @RequestMapping("/")
    @Operation(summary = "Get gyms on all leagues", description = "Get gyms for all leagues")
    fun getAllGyms(): ResponseEntity<List<LeagueGymsDto>> = ResponseEntity.ok(gymService.getAllGyms())

    @RequestMapping("/{league}")
    @Operation(summary = "Get gyms by region", description = "Get gyms by region")
    fun getGymsByLeague(
        @PathVariable league: String,
    ): ResponseEntity<LeagueGymsDto> = ResponseEntity.ok(gymService.getGymsByRegion(league))

    @RequestMapping("/region/{region}")
    @Operation(summary = "Get gyms by region", description = "Get gyms by region")
    fun getGymsByRegion(
        @PathVariable region: String,
    ): ResponseEntity<LeagueGymsDto> = ResponseEntity.ok(gymService.getGymsByRegion(region))
}
