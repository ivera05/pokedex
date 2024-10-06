package com.pokedex.repositories

import com.pokedex.entities.PokemonEntity
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.math.BigInteger

interface PokemonPort {
    fun findTopByHp(limit: Int ): List<PokemonEntity>
    fun findTopByAttack(limit: Int ): List<PokemonEntity>
    fun findTopByDefense(limit: Int ): List<PokemonEntity>
    fun findTopBySpecialAttack(limit: Int ): List<PokemonEntity>
    fun findTopBySpecialDefense(limit: Int ): List<PokemonEntity>
    fun findTopBySpeed(limit: Int ): List<PokemonEntity>
    fun findAll(pageable: Pageable): Page<PokemonEntity>
    fun getTotalCount(): Long
    fun search(query: String, pageable: Pageable ): Page<PokemonEntity>
}

class PokemonPortImpl(
    @PersistenceContext private val entityManager: EntityManager
): PokemonPort {
    override fun findTopByHp(limit: Int ): List<PokemonEntity> {
        val queryStr = """
            SELECT p FROM PokemonEntity p WHERE p.hp IS NOT NULL ORDER BY p.hp DESC
        """.trimIndent()
        val query = entityManager.createQuery(queryStr,PokemonEntity::class.java)
        query.maxResults = limit

        return query.resultList
    }

    override fun findTopByAttack(limit: Int): List<PokemonEntity> {
        val queryStr = """
            SELECT p FROM PokemonEntity p WHERE p.attack IS NOT NULL ORDER BY p.attack DESC
        """.trimIndent()
        val query = entityManager.createQuery(queryStr,PokemonEntity::class.java)

        query.maxResults = limit
        return query.resultList
    }

    override fun findTopByDefense(limit: Int): List<PokemonEntity> {
        val queryStr = """
            SELECT p FROM PokemonEntity p WHERE p.defense IS NOT NULL ORDER BY p.defense DESC
        """.trimIndent()
        val query = entityManager.createQuery(queryStr,PokemonEntity::class.java)
        query.maxResults = limit

        return query.resultList
    }

    override fun findTopBySpecialAttack(limit: Int): List<PokemonEntity> {
        val queryStr = """
            SELECT p FROM PokemonEntity p WHERE p.special_attack IS NOT NULL ORDER BY p.special_attack DESC
        """.trimIndent()
        val query = entityManager.createQuery(queryStr,PokemonEntity::class.java)
        query.maxResults = limit

        return query.resultList
    }

    override fun findTopBySpecialDefense(limit: Int): List<PokemonEntity> {
        val queryStr = """
            SELECT p FROM PokemonEntity p WHERE p.special_defense IS NOT NULL ORDER BY p.special_defense DESC
        """.trimIndent()
        val query = entityManager.createQuery(queryStr,PokemonEntity::class.java)
        query.maxResults = limit

        return query.resultList
    }

    override fun findTopBySpeed(limit: Int): List<PokemonEntity> {
        val queryStr = """
            SELECT p FROM PokemonEntity p WHERE p.speed IS NOT NULL ORDER BY p.speed DESC
        """.trimIndent()
        val query = entityManager.createQuery(queryStr,PokemonEntity::class.java)
        query.maxResults = limit

        return query.resultList
    }

    override fun findAll(pageable: Pageable): Page<PokemonEntity> {
        val queryStr = """
            SELECT p FROM PokemonEntity p ORDER BY p.id ASC
        """.trimIndent()
        val query = entityManager.createQuery(queryStr,PokemonEntity::class.java)

        query.firstResult = pageable.offset.toInt()
        query.maxResults = pageable.pageSize

        val pokemonList = query.resultList

        val total = getTotalCount()
        return PageImpl(pokemonList, pageable, total)
    }

    override fun getTotalCount(): Long {
        var queryStr = """
            SELECT count(p) FROM PokemonEntity p
        """.trimIndent()
        val query = entityManager.createQuery(queryStr, Long::class.java)
        return query.singleResult
    }

    override fun search(fuzzySearch: String, pageable: Pageable): Page<PokemonEntity> {
        val queryStr = """
            SELECT *
            FROM pokemons p
            WHERE
                SIMILARITY(name, :query) > 0.2
            ORDER BY SIMILARITY(name, :query) DESC
        """.trimIndent()
        val query = entityManager.createNativeQuery(queryStr, PokemonEntity::class.java)
        query.setParameter("query", fuzzySearch)

        query.firstResult = pageable.offset.toInt()
        query.maxResults = pageable.pageSize

        @Suppress("UNCHECKED_CAST")
        val pokemonList: List<PokemonEntity> = query.resultList as List<PokemonEntity>

        val countQueryStr = """
            SELECT COUNT(*)
            FROM pokemons p
            WHERE similarity(p.name, :name) > 0.2
        """.trimIndent()

        val countQuery = entityManager.createNativeQuery(countQueryStr, Long::class.java)
        countQuery.setParameter("name", fuzzySearch)
        val total: Long = countQuery.singleResult as Long

        // Return paginated results as PageImpl
        return PageImpl(pokemonList, pageable, total)
    }
}

@Repository
interface PokemonRepository: JpaRepository<PokemonEntity, Long>, PokemonPort
