package com.pokedex.repositories

import com.pokedex.entities.TrainerEntity
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface TrainerPort {
}

class TrainerPortImpl( @PersistenceContext private val entityManager: EntityManager): TrainerPort {
}

@Repository
interface TrainerRepository: JpaRepository<TrainerEntity, Long>, TrainerPort
