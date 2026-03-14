package com.pokedex.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "trainers")
class TrainerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    val user: UserEntity,
    @Column(nullable = false)
    val title: String,
    @Column(nullable = false)
    val bio: String,
    @Column(nullable = false)
    val city: String,
    @Column(name = "region", nullable = false, columnDefinition = "pokemon_league_enum")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    val region: RegionEnum,
    @OneToMany(mappedBy = "trainer", cascade = [CascadeType.PERSIST], fetch = FetchType.LAZY)
    val pokemons: MutableList<CaughtPokemonEntity>,
    @OneToMany(
        mappedBy = "trainer",
        cascade = [CascadeType.PERSIST],
        orphanRemoval = true,
    )
    val badges: MutableList<TrainerBadges> = mutableListOf(),
)
