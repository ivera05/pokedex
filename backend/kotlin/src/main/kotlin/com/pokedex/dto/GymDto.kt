package com.pokedex.dto

import com.pokedex.entity.GymEntity
import com.pokedex.entity.LeagueEnum
import com.pokedex.entity.RegionEnum

data class LeagueGymsDto(
    val league: LeagueEnum,
    val region: RegionEnum,
    val gyms: List<GymDto>,
)

data class GymDto(
    val id: Long,
    val leader: TrainerDto,
    val league: LeagueEnum,
    val region: RegionEnum,
    val city: String,
    val badge: String,
    val image: String,
) {
    companion object {
        fun fromEntity(gyms: GymEntity): GymDto =
            GymDto(
                id = gyms.id,
                leader = TrainerDto.fromEntity(gyms.leader),
                league = gyms.league,
                region = gyms.region,
                city = gyms.city,
                badge = gyms.badge,
                image = gyms.image,
            )
    }
}
