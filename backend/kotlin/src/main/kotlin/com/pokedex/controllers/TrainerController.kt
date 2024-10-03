package com.pokedex.controllers

import com.pokedex.entities.TrainerEntity
import com.pokedex.services.TrainerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TrainerController(
    private val trainerService: TrainerService
) {

    @GetMapping("/api/trainer")
    fun findAll(): List<TrainerEntity> {
        return trainerService.findAll()
    }

    @GetMapping("/api/trainer/{id}")
    fun findById(@PathVariable id: String): TrainerEntity {
        return trainerService.findById(id.toLong())
    }
}