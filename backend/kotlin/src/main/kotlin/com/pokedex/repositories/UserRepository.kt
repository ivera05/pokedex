package com.pokedex.repositories

import com.pokedex.entities.TrainerEntity
import com.pokedex.entities.UserEntity
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface UserPort {
    fun findByUsername(username: String): UserEntity?
}

class UserPortImpl( @PersistenceContext private val entityManager: EntityManager): UserPort {
    override fun findByUsername(username: String): UserEntity? {
        val queryStr = """
            SELECT * FROM UserEntity u WHERE username = :username 
        """.trimIndent()
        val query = entityManager.createQuery(queryStr, UserEntity::class.java)

        return query.singleResult
    }
}

@Repository
interface UserRepository: JpaRepository<UserEntity, Long>, UserPort
