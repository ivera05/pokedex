package com.pokedex.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "trainers")
class TrainerEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String = "",

    @OneToMany(mappedBy = "trainer", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val caughtPokemons: List<CaughtPokemonEntity> = mutableListOf()
)