package com.pokedex.repository

import com.pokedex.entity.GymEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface GymRepository : JpaRepository<GymEntity, Long> {
    @Query(
        "SELECT DISTINCT g FROM GymEntity g WHERE g.league = :league",
        countQuery = "SELECT COUNT(DISTINCT g) FROM GymEntity g WHERE g.league = :league",
    )
    fun findAllByLeague(
        @Param("league") league: String,
    ): List<GymEntity>

    @Query(
        "SELECT DISTINCT g FROM GymEntity g WHERE g.region = :region",
        countQuery = "SELECT COUNT(DISTINCT g) FROM GymEntity g WHERE g.region = :region",
    )
    fun findAllByRegion(
        @Param("region") region: String,
    ): List<GymEntity>
}
