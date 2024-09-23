package com.pokedex.repositories

import com.pokedex.entities.PokemonTypeEntity
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface PokemonTypePort {
    fun findByType(type: String): PokemonTypeEntity?
}

class PokemonTypePortImpl( @PersistenceContext private val entityManager: EntityManager): PokemonTypePort {
    override fun findByType(type: String): PokemonTypeEntity? {
        val query = entityManager.createQuery(
            "SELECT p FROM PokemonTypeEntity p WHERE p.type = :type",
            PokemonTypeEntity::class.java
        )

        return query.singleResult
    }
}

@Repository
interface PokemonTypeRepository: JpaRepository<PokemonTypeEntity, Long>, PokemonTypePort
