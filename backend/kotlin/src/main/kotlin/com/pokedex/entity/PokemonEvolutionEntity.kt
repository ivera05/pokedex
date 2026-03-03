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
@Table(name = "pokemon_evolutions")
class PokemonEvolutionEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "from_pokemon_id", nullable = false)
    val fromPokemon: PokemonEntity,

    @ManyToOne
    @JoinColumn(name = "to_pokemon_id", nullable = false)
    val toPokemon: PokemonEntity,

    @Column(nullable = false)
    val trigger: String
){
}