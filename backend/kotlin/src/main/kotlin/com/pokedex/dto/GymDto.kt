package com.pokedex.dto

import com.pokedex.entity.GymEntity

data class LeagueGymsDto(
    val league: String,
    val region: String,
    val gyms: List<GymDto>,
) {
    companion object {
        fun fromEntity(gyms: List<GymEntity>): LeagueGymsDto =
            LeagueGymsDto(
                league = gyms.first().league,
                region = gyms.first().region,
                gyms = gyms.map { GymDto.fromEntity(it) },
            )
    }
}

data class GymDto(
    val id: Long,
    val leader: TrainerDto,
    val league: String,
    val region: String,
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
