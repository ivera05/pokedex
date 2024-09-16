package com.pokedex.services


import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.pokedex.dtos.AbilityDto
import com.pokedex.dtos.BaseDto
import com.pokedex.dtos.EvolutionDto
import com.pokedex.dtos.EvolutionsDto
import com.pokedex.dtos.ImagesDto
import com.pokedex.dtos.PokemonDto
import com.pokedex.dtos.PokemonSyncDto
import com.pokedex.dtos.ProfileDto
import com.pokedex.entities.PokemonEntity
import com.pokedex.entities.PokemonTypeEntity
import com.pokedex.repositories.PokemonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class PokemonService @Autowired constructor(
    private val objectMapper: ObjectMapper,
    private val pokemonRepository: PokemonRepository,
    private val pokemonTypeService: PokemonTypeService,
    private val webClient: WebClient
) {

    fun save(pokemonEntity: PokemonEntity): PokemonEntity {
        return pokemonRepository.save<PokemonEntity>(pokemonEntity)
    }

    fun saveAll(pokemonList: List<PokemonEntity>) {
        pokemonRepository.saveAll(pokemonList)
    }

    fun findById(id: Long): PokemonDto {
        val pokemon = pokemonRepository.findById(id).orElseThrow { IllegalArgumentException("Pokemon with ID $id not found") }
        return PokemonDto(
            id = pokemon.id!!,
            name = pokemon.name!!,
            type = pokemon.types!!.map {
                it.type!!
            },
            description = pokemon.description!!,
            species = pokemon.species!!,
            base = pokemon.base,
            evolutions = pokemon.evolutions!!,
            profile = pokemon.profile!!,
            images = pokemon.images!!
        )
    }

    fun findAll(): List<PokemonDto> {
        return pokemonRepository.findAll().map {
            PokemonDto(
                id = it.id!!,
                name = it.name!!,
                type = it.types!!.map {
                    it.type!!
                },
                description = it.description!!,
                species = it.species!!,
                base = it.base,
                evolutions = it.evolutions!!,
                profile = it.profile!!,
                images = it.images!!
            )
            }.sortedBy { it.id }
    }

    fun sync(jsonUrl: String) {
        try {
            val pokemonList = fetchJsonFromUrl(jsonUrl)

            pokemonList.forEach({ pokemon: PokemonSyncDto ->
                val types: List<PokemonTypeEntity> = pokemon.type.map { pokemonTypeService.findByType( it ) }

                var base: BaseDto? = null
                if (pokemon.base != null) {
                    base = BaseDto(
                        hp = pokemon.base.hp,
                        attack = pokemon.base.attack,
                        defense = pokemon.base.defense,
                        specialAttack = pokemon.base.specialAttack,
                        specialDefense = pokemon.base.specialDefense,
                        speed = pokemon.base.speed
                    )
                }

                // Build Evolution Dto
                val evolutions: EvolutionsDto = EvolutionsDto (prev = null, next = null)
                if (pokemon.evolution.prev != null) {
                    evolutions.prev = EvolutionDto(
                        id = pokemon.evolution.prev[0].toLong(),
                        condition = pokemon.evolution.prev[1]
                    )
                }
                if (pokemon.evolution.next != null) {
                    evolutions.next = pokemon.evolution.next.map {
                        EvolutionDto(
                            id = it[0].toLong(),
                            condition = it[1]
                        )
                    }
                }

                val profile = ProfileDto(
                    height = pokemon.profile.height,
                    weight = pokemon.profile.weight,
                    ability = pokemon.profile.ability.map {
                        AbilityDto(
                            ability = it[0],
                            isHidden = it[1].toBoolean()
                        )
                    }
                )

                val pokemonEntity = PokemonEntity(
                    id = pokemon.id,
                    name = pokemon.name.english,
                    types = types,
                    base = base,
                    species = pokemon.species,
                    description = pokemon.description,
                    evolutions = evolutions,
                    profile = profile,
                    images = ImagesDto(
                        sprite = pokemon.image.sprite,
                        thumbnail = pokemon.image.thumbnail,
                        hires = pokemon.image.hires
                    ),
                )
                save(pokemonEntity)
            })
        } catch (ex: Exception) {
            throw Exception("Error while syncing Pokémon data: ${ex.message}")
        }
    }

    private fun fetchJsonFromUrl(url: String): List<PokemonSyncDto> {
        return webClient.get()
            .uri(url)
            .accept(MediaType.TEXT_PLAIN)
            .retrieve()
            .bodyToMono(String::class.java)
            .map { json ->
                objectMapper.readValue(json, object : TypeReference<List<PokemonSyncDto>>() {})
            }
            .block() ?: emptyList()
    }
}