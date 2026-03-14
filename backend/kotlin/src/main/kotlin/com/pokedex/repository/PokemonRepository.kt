package com.pokedex.repository

import com.pokedex.entity.PokemonEntity
import com.pokedex.entity.PokemonTypeEnum
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PokemonRepository : JpaRepository<PokemonEntity, Long> {
    @Query(
        "SELECT p FROM PokemonEntity p JOIN p.types t WHERE t = :type",
        countQuery = "SELECT COUNT(DISTINCT p) FROM PokemonEntity p JOIN p.types t WHERE t = :type",
    )
    fun findAllByType(
        @Param("type") type: String,
        pageable: Pageable,
    ): Page<PokemonEntity>

    @Query(
        """
    SELECT DISTINCT p
    FROM PokemonEntity p
    LEFT JOIN p.types t
    WHERE (CAST(:name AS string) IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', CAST(:name AS string), '%')))
      AND (CAST(:type AS string) IS NULL OR t = :type)
    ORDER BY p.id
    """,
        countQuery = """
    SELECT COUNT(DISTINCT p)
    FROM PokemonEntity p
    LEFT JOIN p.types t
    WHERE (CAST(:name AS string) IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', CAST(:name AS string), '%')))
      AND (CAST(:type AS string) IS NULL OR t = :type)
    """,
    )
    fun findAllByFilters(
        @Param("name") name: String?,
        @Param("type") type: PokemonTypeEnum?,
        pageable: Pageable,
    ): Page<PokemonEntity>
}
