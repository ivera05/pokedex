package com.pokedex.entities

import com.pokedex.dtos.BaseDto
import com.pokedex.dtos.EvolutionsDto
import com.pokedex.dtos.ImagesDto
import com.pokedex.dtos.ProfileDto
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
    var evolutions: EvolutionsDto? = null,

    @Type(JsonType::class)
    @Column(name = "base", columnDefinition = "jsonb")
    var base: BaseDto? = null,

    @Type(JsonType::class)
    @Column(name = "profile", columnDefinition = "jsonb")
    var profile: ProfileDto? = null,

    @Type(JsonType::class)
    @Column(name = "image", columnDefinition = "jsonb")
    var images: ImagesDto? = null,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "pokemon_type_types",
        joinColumns = [JoinColumn(name = "pokemon_id")],
        inverseJoinColumns = [JoinColumn(name = "type_id")]
    )
    var types: List<PokemonTypeEntity>? = null
)
