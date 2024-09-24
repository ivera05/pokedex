package com.pokedex.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "pokemons")
class PokemonEntity(
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    var id: Long = 0L,

    @Column(name = "name")
    var name: String = "",

    @Column(name = "species")
    var species: String = "",

    @Column(name = "description")
    var description: String = "",

    @Column(name = "hp")
    var hp: Int? = null,

    @Column(name = "attack")
    var attack: Int? = null,

    @Column(name = "defense")
    var defense: Int? = null,

    @Column(name = "special_attack")
    var specialAttack: Int? = null,

    @Column(name = "special_defense")
    var specialDefense: Int? = null,

    @Column(name = "speed")
    var speed: Int? = null,

    @OneToMany(mappedBy = "pokemon", fetch = FetchType.LAZY)
    var evolutions: List<PokemonEvolutionEntity> = mutableListOf<PokemonEvolutionEntity>(),

    @Column(name = "height")
    var height: String = "",

    @Column(name = "weight")
    var weight: String = "",

    @OneToMany(mappedBy = "pokemon", fetch = FetchType.LAZY)
    var abilities: List<PokemonAbilityEntity> = emptyList<PokemonAbilityEntity>(),

    @Column(name = "sprites")
    var sprite: String? = null,

    @Column(name = "thumbnail")
    var thumbnail: String? = null,

    @Column(name = "hires")
    var hires: String? = null,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "pokemon_type_types",
        joinColumns = [JoinColumn(name = "pokemon_id")],
        inverseJoinColumns = [JoinColumn(name = "type_id")]
    )
    var types: List<PokemonTypeEntity> = emptyList<PokemonTypeEntity>()
)
