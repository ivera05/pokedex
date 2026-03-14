package com.pokedex.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "gyms")
class GymEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id", nullable = false, unique = true)
    val leader: TrainerEntity,
    @Column(name = "league", nullable = false, columnDefinition = "pokemon_league_enum")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    val league: LeagueEnum,
    @Column(name = "region", nullable = false, columnDefinition = "pokemon_league_enum")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    val region: RegionEnum,
    @Column(nullable = false)
    val city: String,
    @Column(nullable = false)
    val badge: String,
    @Column(nullable = false)
    val image: String,
)

enum class LeagueEnum {
    Indigo,
    Johto,
    Kanto,
    Hoenn,
    Sinnoh,
    Unova,
    Kalos,
    Alola,
    Galar,
    Paldea,
    UNKNOWN,
}

enum class RegionEnum {
    Indigo,
    Johto,
    Kanto,
    Hoenn,
    Sinnoh,
    Unova,
    Kalos,
    Alola,
    Galar,
    Paldea,
    UNKNOWN,
}
