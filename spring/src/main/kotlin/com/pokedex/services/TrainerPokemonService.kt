package com.pokedex.services

import com.pokedex.entities.TrainerPokemonEntity
import com.pokedex.repositories.TrainerPokemonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TrainerPokemonService @Autowired constructor(
    private val trainerPokemonRepo: TrainerPokemonRepository
) {

    fun findByTrainerId(trainerId: Long): List<TrainerPokemonEntity> {
        return trainerPokemonRepo.findByTrainerId(trainerId)
    }
}