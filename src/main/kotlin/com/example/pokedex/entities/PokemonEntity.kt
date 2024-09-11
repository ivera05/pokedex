package com.example.pokedex.entities

import com.example.pokedex.dtos.BaseDto
import com.example.pokedex.dtos.EvolutionDto
import com.example.pokedex.dtos.ImageDto
import com.example.pokedex.dtos.ProfileDto
import com.vladmihalcea.hibernate.type.json.JsonType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import org.hibernate.annotations.Type

@Entity
@Table(name = "pokemons")
class PokemonEntity(
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    var id: Long? = null,

    @Column(name = "name")
    var name: String? = null,

    @Column(name = "species")
    var species: String? = null,

    @Column(name = "description")
    var description: String? = null,

    @Type(JsonType::class)
    @Column(name = "evolutions", columnDefinition = "jsonb")
    var evolution: EvolutionDto,

    @Type(JsonType::class)
    @Column(name = "base", columnDefinition = "jsonb")
    var base: BaseDto,

    @Type(JsonType::class)
    @Column(name = "profile", columnDefinition = "jsonb")
    var profile: ProfileDto,

    @Type(JsonType::class)
    @Column(name = "image", columnDefinition = "jsonb")
    var image: ImageDto,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "pokemon_type_types",
        joinColumns = [JoinColumn(name = "pokemon_id")],
        inverseJoinColumns = [JoinColumn(name = "type_id")]
    )
    var types: MutableSet<PokemonTypeEntity> = mutableSetOf()
)
