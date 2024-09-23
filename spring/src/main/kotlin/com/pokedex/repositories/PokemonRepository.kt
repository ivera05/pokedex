package com.pokedex.repositories

import com.pokedex.entities.PokemonEntity
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface PokemonPort {
    fun findTopByHp(limit: Int? ): List<PokemonEntity>
    fun findTopByAttack(limit: Int? ): List<PokemonEntity>
    fun findTopByDefense(limit: Int? ): List<PokemonEntity>
    fun findTopBySpecialAttack(limit: Int? ): List<PokemonEntity>
    fun findTopBySpecialDefense(limit: Int? ): List<PokemonEntity>
    fun findTopBySpeed(limit: Int? ): List<PokemonEntity>
    fun findAll( cursor: Int?, limit: Int? ): List<PokemonEntity>
}

class PokemonPortImpl( @PersistenceContext private val entityManager: EntityManager): PokemonPort {
    override fun findTopByHp(limit: Int? ): List<PokemonEntity> {
        val queryStr = """
            SELECT p FROM PokemonEntity p WHERE p.hp IS NOT NULL ORDER BY p.hp DESC
        """.trimIndent()
        val query = entityManager.createQuery(queryStr,PokemonEntity::class.java)

        if (limit != null) {
            query.maxResults = limit
        }

        return query.resultList
    }

    override fun findTopByAttack(limit: Int?): List<PokemonEntity> {
        val queryStr = """
            SELECT p FROM PokemonEntity p WHERE p.attack IS NOT NULL ORDER BY p.attack DESC
        """.trimIndent()
        val query = entityManager.createQuery(queryStr,PokemonEntity::class.java)

        if (limit != null) {
            query.maxResults = limit
        }

        return query.resultList
    }

    override fun findTopByDefense(limit: Int?): List<PokemonEntity> {
        val queryStr = """
            SELECT p FROM PokemonEntity p WHERE p.defense IS NOT NULL ORDER BY p.defense DESC
        """.trimIndent()
        val query = entityManager.createQuery(queryStr,PokemonEntity::class.java)

        if (limit != null) {
            query.maxResults = limit
        }

        return query.resultList
    }

    override fun findTopBySpecialAttack(limit: Int?): List<PokemonEntity> {
        val queryStr = """
            SELECT p FROM PokemonEntity p WHERE p.special_attack IS NOT NULL ORDER BY p.special_attack DESC
        """.trimIndent()
        val query = entityManager.createQuery(queryStr,PokemonEntity::class.java)

        if (limit != null) {
            query.maxResults = limit
        }

        return query.resultList
    }

    override fun findTopBySpecialDefense(limit: Int?): List<PokemonEntity> {
        val queryStr = """
            SELECT p FROM PokemonEntity p WHERE p.special_defense IS NOT NULL ORDER BY p.special_defense DESC
        """.trimIndent()
        val query = entityManager.createQuery(queryStr,PokemonEntity::class.java)

        if (limit != null) {
            query.maxResults = limit
        }

        return query.resultList
    }

    override fun findTopBySpeed(limit: Int?): List<PokemonEntity> {
        val queryStr = """
            SELECT p FROM PokemonEntity p WHERE p.speed IS NOT NULL ORDER BY p.speed DESC
        """.trimIndent()
        val query = entityManager.createQuery(queryStr,PokemonEntity::class.java)

        if (limit != null) {
            query.maxResults = limit
        }

        return query.resultList
    }

    override fun findAll( cursor: Int?, limit: Int? ): List<PokemonEntity> {
        val queryStr = """
            SELECT p FROM pokemon p WHERE p.id > :cursor ORDER BY p.id ASC
        """.trimIndent()
        val query = entityManager.createQuery(queryStr,PokemonEntity::class.java)
        query.setParameter("cursor", cursor ?: 0)

        if (limit != null) {
            query.maxResults = limit
        }

        return query.resultList
    }
}

@Repository
interface PokemonRepository: JpaRepository<PokemonEntity, Long>, PokemonPort
