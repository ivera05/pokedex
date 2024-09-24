package com.pokedex.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "pokemon_evolutions")
class PokemonEvolutionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Ensure auto-generation of ID
    var id: Long = 0L,

    @ManyToOne()
    @JoinColumn(name="pokemon_id", nullable = false)
    var pokemon: PokemonEntity = PokemonEntity(),

    @Column(name = "evolution_id")
    var evolutionId: Long = 0L,

    @Column(name = "condition")
    var condition: String = "",

    @Column(name = "is_prev")
    var isPrev: Boolean = false,

    @Column(name = "is_next")
    var isNext: Boolean = false,
)