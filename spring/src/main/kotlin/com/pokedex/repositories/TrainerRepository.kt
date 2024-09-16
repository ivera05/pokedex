package com.pokedex.repositories

import com.pokedex.entities.TrainerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TrainerRepository: JpaRepository<TrainerEntity, Long>