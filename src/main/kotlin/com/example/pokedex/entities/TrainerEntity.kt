package com.example.pokedex.entities

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
import jakarta.persistence.Table

@Entity
@Table( name = "trainers")
class TrainerEntity() {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    var id: Long? = null

    @Column(name = "name")
    var name: String? = null

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "trainer_pokemons",
        joinColumns = [JoinColumn(name = "trainer_id")],
        inverseJoinColumns = [JoinColumn(name = "pokemon_id")]
    )
    var pokemons: MutableSet<PokemonEntity> = mutableSetOf()
}