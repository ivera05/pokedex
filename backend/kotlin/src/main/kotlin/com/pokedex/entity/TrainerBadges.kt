package com.pokedex.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "trainer_badges")
class TrainerBadges(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id", nullable = false)
    val trainer: TrainerEntity,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id", nullable = false)
    val gym: GymEntity,
)
