package com.pokedex.repository

import com.pokedex.entity.TrainerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TrainerRepository : JpaRepository<TrainerEntity, Long> {
    fun findByUserId(userId: Long): TrainerEntity?
}
