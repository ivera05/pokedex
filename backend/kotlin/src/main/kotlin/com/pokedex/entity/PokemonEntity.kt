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
@Table(name = "pokemons")
class PokemonEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(nullable = false)
    val name: String,
    @OneToMany(mappedBy = "pokemon", cascade = [CascadeType.PERSIST], orphanRemoval = true)
    val types: MutableSet<PokemonTypeEntity> = mutableSetOf(),
    @Column(name = "base_hp", nullable = false)
    val baseHP: Short,
    @Column(nullable = false)
    val baseAttack: Short,
    @Column(nullable = false)
    val baseDefense: Short,
    @Column(nullable = false)
    val baseSpecialAttack: Short,
    @Column(nullable = false)
    val baseSpecialDefense: Short,
    @Column(nullable = false)
    val baseSpeed: Short,
    @Column(nullable = false)
    val species: String,
    @Column(columnDefinition = "TEXT")
    val description: String,
    @Column(nullable = false)
    val height: Float,
    @Column(nullable = false)
    val weight: Float,
    @OneToMany(mappedBy = "pokemon", cascade = [CascadeType.PERSIST], orphanRemoval = true)
    val abilities: MutableSet<PokemonAbilityEntity> = mutableSetOf(),
    @OneToMany(mappedBy = "fromPokemon", fetch = FetchType.LAZY)
    val evolutions: MutableList<PokemonEvolutionEntity>,
    @OneToMany(mappedBy = "toPokemon", fetch = FetchType.LAZY)
    val previousEvolutions: MutableList<PokemonEvolutionEntity>,
    @Column(nullable = false)
    val image: String,
)
