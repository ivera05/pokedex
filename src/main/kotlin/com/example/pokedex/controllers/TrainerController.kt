package com.example.pokedex.controllers

import com.example.pokedex.entities.TrainerEntity
import com.example.pokedex.services.TrainerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TrainerController(
    private val trainerService: TrainerService
) {

    @GetMapping("/trainer")
    fun findAll(): List<TrainerEntity> {
        return trainerService.findAll()
    }

    @GetMapping("/trainer/{id}")
    fun findById(@PathVariable id: String): TrainerEntity {
        return trainerService.findById(id.toLong())
    }
}