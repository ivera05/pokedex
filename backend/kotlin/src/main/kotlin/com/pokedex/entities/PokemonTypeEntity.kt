package com.pokedex.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Type
import com.vladmihalcea.hibernate.type.json.JsonType

@Entity
@Table( name = "pokemon_types")
class PokemonTypeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @Column(name = "name", unique = true, nullable = false)
    var type: String = "",

    @Type(JsonType::class)
    @Column(name = "effective", columnDefinition = "jsonb")
    var effective: List<String> = emptyList(),

    @Type(JsonType::class)
    @Column(name = "ineffective", columnDefinition = "jsonb")
    var ineffective: List<String> = emptyList(),

    @Type(JsonType::class)
    @Column(name = "no_effect", columnDefinition = "jsonb")
    var noEffect: List<String> = emptyList(),
)