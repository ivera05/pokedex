package com.pokedex.repository

import com.pokedex.entity.TrainerEntity
import com.pokedex.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TrainerRepository: JpaRepository<TrainerEntity, Long> {
    fun findByUserId(userId: Long): TrainerEntity?
    fun User(user: UserEntity): MutableList<TrainerEntity>
}