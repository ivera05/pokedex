package com.pokedex.entities

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.OrderBy
import jakarta.persistence.Table

@Entity
@Table( name = "trainers")
class TrainerEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    @Column(name = "name")
    var name: String = ""

    @OneToMany(
        mappedBy = "trainer",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY
    )
    @OrderBy("createdAt ASC")
    var trainerPokemons: List<TrainerPokemonEntity> = emptyList<TrainerPokemonEntity>()
}