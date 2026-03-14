package com.pokedex.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "pokemon_types")
class PokemonTypeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(nullable = false, columnDefinition = "pokemon_type_enum")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    val type: PokemonTypeEnum,
    @ManyToOne
    @JoinColumn(name = "pokemon_id", nullable = false)
    val pokemon: PokemonEntity,
)

enum class PokemonTypeEnum {
    Bug,
    Dark,
    Dragon,
    Electric,
    Fairy,
    Fighting,
    Fire,
    Flying,
    Ghost,
    Grass,
    Ground,
    Ice,
    Normal,
    Poison,
    Psychic,
    Rock,
    Steel,
    Water,
}
