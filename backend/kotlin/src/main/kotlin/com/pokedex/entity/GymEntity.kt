package com.pokedex.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "gyms")
class GymEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id", nullable = false, unique = true)
    val leader: TrainerEntity,
    @Column(nullable = false)
    val league: String,
    @Column(nullable = false)
    val region: String,
    @Column(nullable = false)
    val city: String,
    @Column(nullable = false)
    val badge: String,
    @Column(nullable = false)
    val image: String,
)
