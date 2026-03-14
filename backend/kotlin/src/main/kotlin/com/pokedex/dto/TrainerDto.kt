package com.pokedex.dto

import com.pokedex.entity.RegionEnum
import com.pokedex.entity.TrainerEntity

data class TrainerDto(
    val id: Long,
    val email: String,
    val displayName: String,
    val title: String,
    val bio: String,
    val city: String,
    val region: RegionEnum,
    val badges: Int,
    val avatar: String,
    val pokemons: List<PokemonDto>,
) {
    companion object {
        fun fromEntity(trainer: TrainerEntity): TrainerDto =
            TrainerDto(
                id = trainer.id,
                email = trainer.user.username,
                displayName = trainer.user.name,
                title = trainer.title,
                bio = trainer.bio,
                city = trainer.city,
                region = trainer.region,
                badges = trainer.badges.count(),
                avatar = trainer.user.avatar,
                pokemons = trainer.pokemons.map { PokemonDto.fromEntity(it.pokemon) },
            )
    }
}
