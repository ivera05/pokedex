package com.pokedex.service

import com.pokedex.dto.GymDto
import com.pokedex.dto.LeagueGymsDto
import com.pokedex.dto.TrainerDto
import com.pokedex.repository.GymRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class GymService(
    private val gymRepository: GymRepository,
) {
    @Cacheable(value = ["gym"])
    fun getAllGyms(): List<LeagueGymsDto> {
        val allGyms = gymRepository.findAll()
        val gymsByLeague = allGyms.groupBy { it.league }

        return gymsByLeague.map { (leagueName, entities) ->
            LeagueGymsDto(
                league = leagueName,
                region = entities.first().region,
                gyms = entities.map { GymDto.fromEntity(it) },
            )
        }
    }

    @Cacheable(value = ["gym"], key = "#region")
    fun getGymsByRegion(region: String): LeagueGymsDto {
        val gymEntityList = gymRepository.findAllByRegion(region)

        val gymDtoList =
            gymEntityList.map { gymEntity ->
                GymDto(
                    id = gymEntity.id,
                    league = gymEntity.league,
                    region = gymEntity.region,
                    city = gymEntity.city,
                    badge = gymEntity.badge,
                    image = gymEntity.image,
                    leader =
                        gymEntity.leader.let {
                            TrainerDto.fromEntity(gymEntity.leader)
                        },
                )
            }

        return LeagueGymsDto(
            league = gymDtoList.first().league,
            region = gymDtoList.first().region,
            gyms = gymDtoList,
        )
    }
}
