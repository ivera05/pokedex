package com.pokedex.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "caught_pokemons")
class CaughtPokemonEntity (
    @Id
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id", nullable = false)
    val trainer: TrainerEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pokemon_id", nullable = false)
    val pokemon: PokemonEntity
){
}
