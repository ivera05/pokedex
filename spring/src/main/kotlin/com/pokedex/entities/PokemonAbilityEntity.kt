package com.pokedex.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "pokemon_abilities")
class PokemonAbilityEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Ensure auto-generation of ID
    var id: Long? = null,

    @ManyToOne()
    @JoinColumn(name="pokemon_id", nullable = false)
    var pokemon: PokemonEntity = PokemonEntity(),

    @Column(name = "ability")
    var ability: String = "",

    @Column(name = "is_hidden")
    var isHidden: Boolean = false,
)