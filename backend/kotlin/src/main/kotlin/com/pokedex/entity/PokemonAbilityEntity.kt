package com.pokedex.entity

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
class PokemonAbilityEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @ManyToOne
    @JoinColumn(name = "pokemon_id", nullable = false)
    val pokemon: PokemonEntity,
    @Column(name = "ability", nullable = false)
    val ability: String,
)
