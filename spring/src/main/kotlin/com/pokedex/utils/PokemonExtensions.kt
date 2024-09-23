package com.pokedex.utils

import com.pokedex.dtos.AbilityDto
import com.pokedex.dtos.BaseDto
import com.pokedex.dtos.EvolutionDto
import com.pokedex.dtos.EvolutionsDto
import com.pokedex.dtos.ImagesDto
import com.pokedex.dtos.PokemonDto
import com.pokedex.dtos.ProfileDto
import com.pokedex.entities.PokemonEntity
import com.pokedex.entities.PokemonEvolutionEntity

fun PokemonEntity.toPokemonDto(): PokemonDto {
    val prevEvolEntity: PokemonEvolutionEntity? = evolutions.filter { it.isPrev }.getOrNull(0)
    var prevEvolDto: EvolutionDto? = null
    if (prevEvolEntity != null) {
        prevEvolDto = EvolutionDto(
            id = prevEvolEntity.evolutionId,
            condition = prevEvolEntity.condition,
        )
    }
    var baseDto: BaseDto? = null
    if (hp != null ) {
        baseDto = BaseDto(
            hp = hp!!,
            attack = attack!!,
            defense = defense!!,
            specialAttack = specialAttack!!,
            specialDefense = specialDefense!!,
            speed = speed!!,
        )
    }

    return PokemonDto(
        id = id,
        name = name,
        type = types.map {
            it.type
        },
        description = description,
        species = species,
        base = baseDto,
        evolutions = EvolutionsDto(
            prev = prevEvolDto,
            next = evolutions.filter { it.isNext }.map {
                EvolutionDto(
                    id = prevEvolEntity?.evolutionId,
                    condition = prevEvolEntity?.condition ?: "",
                )
            }
        ),
        profile = ProfileDto(
            height = height,
            weight = weight,
            abilities = abilities.map { AbilityDto(
                ability = it.ability,
                isHidden = it.isHidden,
            ) }
        ),
        images = ImagesDto(
            sprite = sprite,
            thumbnail = thumbnail,
            hires = hires
        )
    )
}