package com.example.pokedex.services

import com.example.pokedex.entities.TrainerEntity
import com.example.pokedex.repositories.TrainerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TrainerService @Autowired constructor(
    private val trainerRepository: TrainerRepository
) {

    fun add(trainerEntity: TrainerEntity): TrainerEntity {
        return trainerRepository.save<TrainerEntity>(trainerEntity)
    }

    fun findById(id: Long): TrainerEntity {
        return trainerRepository.findById(id).orElseThrow { IllegalArgumentException("Trainer not found") }
    }

    fun findAll(): List<TrainerEntity> {
        return trainerRepository.findAll()
    }
}