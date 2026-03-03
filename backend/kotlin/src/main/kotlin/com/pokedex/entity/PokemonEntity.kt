package com.pokedex.entity

import com.pokedex.dto.EvolutionDto
import com.pokedex.dto.PokemonDto
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "pokemons")
class PokemonEntity (
    @Id
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    @ElementCollection
    @CollectionTable(name = "pokemon_types", joinColumns = [JoinColumn(name = "pokemon_id")])
    @Column(name = "type")
    val types: List<String>,

    @Column( name = "base_hp", nullable = false)
    val baseHP: Int,

    @Column(nullable = false)
    val baseAttack: Int,

    @Column(nullable = false)
    val baseDefense: Int,

    @Column(nullable = false)
    val baseSpecialAttack: Int,

    @Column(nullable = false)
    val baseSpecialDefense: Int,

    @Column(nullable = false)
    val baseSpeed: Int,

    @Column(nullable = false)
    val species: String,

    @Column(columnDefinition = "TEXT")
    val description: String,

    @Column(nullable = false)
    val height: Float,

    @Column(nullable = false)
    val weight: Float,

    @ElementCollection
    @CollectionTable(name = "pokemon_abilities", joinColumns = [JoinColumn(name = "pokemon_id")])
    @Column(name = "ability")
    val abilities: List<String>,

    @OneToMany(mappedBy = "fromPokemon", fetch = FetchType.LAZY)
    val evolutions: List<PokemonEvolutionEntity> = mutableListOf(),

    @OneToMany(mappedBy = "toPokemon", fetch = FetchType.LAZY)
    val previousEvolutions: List<PokemonEvolutionEntity> = mutableListOf(),

    @Column(nullable = false)
    val image: String,
) {
    fun toDto(): PokemonDto =
        PokemonDto(
            id = id,
            name = name,
            types = types,
            baseHP = baseHP,
            baseAttack = baseAttack,
            baseDefense = baseDefense,
            baseSpecialAttack = baseSpecialAttack,
            baseSpecialDefense = baseSpecialDefense,
            baseSpeed = baseSpeed,
            description = description,
            species = species,
            height = height,
            weight = weight,
            abilities = abilities,
            image = image,
            evolutions = evolutions.map { evo ->
                EvolutionDto(
                    id = evo.toPokemon.id,
                    name = evo.toPokemon.name,
                    trigger = evo.trigger
                )
            },
            previousEvolutions = previousEvolutions.map { evo ->
                EvolutionDto(
                    id = evo.fromPokemon.id,
                    name = evo.fromPokemon.name,
                    trigger = evo.trigger
                )
            }
        )
}