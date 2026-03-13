package com.pokedex.dto

import com.pokedex.entity.TrainerEntity

data class TrainerDto(
    val id: Long,
    val email: String,
    val displayName: String,
    val title: String,
    val bio: String,
    val region: String,
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
                region = trainer.region,
                badges = trainer.wonBadges.count(),
                avatar = trainer.user.avatar,
                pokemons = trainer.caughtPokemons.map { PokemonDto.fromEntity(it.pokemon) },
            )
    }
}
