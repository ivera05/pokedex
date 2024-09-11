package com.example.pokedex.repositories

import com.example.pokedex.entities.TrainerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TrainerRepository: JpaRepository<TrainerEntity, Long>