package com.pokedex.repositories

import com.pokedex.entities.TrainerPokemonEntity
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface TrainerPokemonPort {
    fun findByTrainerId(trainerId: Long): List<TrainerPokemonEntity>
}

class TrainerPokemonPortImpl( @PersistenceContext private val entityManager: EntityManager): TrainerPokemonPort {
    override fun findByTrainerId(trainerId: Long): List<TrainerPokemonEntity> {
        val query = entityManager.createQuery(
            "SELECT t FROM TrainerPokemonEntity t WHERE t.trainer_id = :trainerId",
            TrainerPokemonEntity::class.java
        )

        return query.resultList
    }
}

@Repository
interface TrainerPokemonRepository: JpaRepository<TrainerPokemonEntity, Long>, TrainerPokemonPort
