package com.pokedex.entity

import com.pokedex.dto.TrainerDto
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

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
    val title: String = "",
    @Column(nullable = false)
    val region: String = "",
    @OneToMany(mappedBy = "trainer", cascade = [CascadeType.PERSIST], fetch = FetchType.LAZY)
    val caughtPokemons: List<CaughtPokemonEntity> = mutableListOf(),
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "won_badges",
        joinColumns = [JoinColumn(name = "trainer_id")],
        inverseJoinColumns = [JoinColumn(name = "gym_id")],
    )
    val wonBadges: List<GymEntity> = mutableListOf(),
) {
    fun toDto(): TrainerDto =
        TrainerDto(
            id = id,
            email = user.username,
            displayName = user.name,
            title = title,
            region = region,
            badges = wonBadges.count(),
            avatar = user.avatar,
        )
}
