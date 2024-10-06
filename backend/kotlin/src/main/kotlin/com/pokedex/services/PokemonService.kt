package com.pokedex.services


import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.pokedex.dtos.PokemonDto
import com.pokedex.dtos.PokemonSyncDto
import com.pokedex.entities.PokemonAbilityEntity
import com.pokedex.entities.PokemonEntity
import com.pokedex.entities.PokemonEvolutionEntity
import com.pokedex.entities.PokemonTypeEntity
import com.pokedex.repositories.PokemonAbilityRepository
import com.pokedex.repositories.PokemonEvolutionRepository
import com.pokedex.repositories.PokemonRepository
import com.pokedex.repositories.PokemonTypeRepository
import com.pokedex.utils.toPokemonDto
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.nio.file.Files
import java.nio.file.Paths

@Service
class PokemonService @Autowired constructor(
    private val pokemonRepo: PokemonRepository,
    private val pokemonAbilityRepo: PokemonAbilityRepository,
    private val pokemonTypeRepo: PokemonTypeRepository,
    private val pokemonEvolutionRepo: PokemonEvolutionRepository,
) {
    private val JSON_FILENAME: String = "static/pokedex.json"
    private val objectMapper = jacksonObjectMapper()

    fun save(pokemonEntity: PokemonEntity): PokemonEntity {
        return pokemonRepo.save<PokemonEntity>(pokemonEntity)
    }

    fun findById(id: Long): PokemonDto {
        val pokemon = pokemonRepo.findById(id).orElseThrow { IllegalArgumentException("Pokemon with ID $id not found") }
        return pokemon.toPokemonDto()
    }

    fun findTopByHp(size: Int): List<PokemonDto> {
        return pokemonRepo.findTopByHp( limit = size ).map { it.toPokemonDto() }
    }

    fun findTopByAttack(size: Int): List<PokemonDto> {
        return pokemonRepo.findTopByAttack( limit = size ).map { it.toPokemonDto() }
    }

    fun findTopByDefense(size: Int): List<PokemonDto> {
        return pokemonRepo.findTopByDefense( limit = size ).map { it.toPokemonDto() }
    }

    fun findTopBySpecialAttack(size: Int): List<PokemonDto> {
        return pokemonRepo.findTopBySpecialAttack( limit = size ).map { it.toPokemonDto() }
    }

    fun findTopBySpecialDefense(size: Int): List<PokemonDto> {
        return pokemonRepo.findTopBySpecialDefense( limit = size ).map { it.toPokemonDto() }
    }

    fun findTopBySpeed(size: Int): List<PokemonDto> {
        return pokemonRepo.findTopBySpeed( limit = size ).map { it.toPokemonDto() }
    }

    fun findAll(page: Int, size: Int): Page<PokemonDto> {
        val pageable: Pageable = PageRequest.of(page, size)
        return pokemonRepo.findAll(pageable).map { it.toPokemonDto() }
    }

    fun search(query: String, page: Int, size: Int): Page<PokemonDto> {
        val pageable: Pageable = PageRequest.of(page, size)
        return pokemonRepo
            .search( query = query, pageable = pageable )
            .map { it.toPokemonDto() }
    }

    fun sync() {
        try {
            val pokemonSyncDtoList: List<PokemonSyncDto> = fetchPokemonListFromJson()
            val pokemonTypesEntity: List<PokemonTypeEntity> = pokemonTypeRepo.findAll()

            pokemonSyncDtoList.forEach { pokemonSyncDto: PokemonSyncDto ->
                // Base
                val hp = pokemonSyncDto.base?.hp
                val attack = pokemonSyncDto.base?.attack
                val defense = pokemonSyncDto.base?.defense
                val specialAttack = pokemonSyncDto.base?.specialAttack
                val specialDefense = pokemonSyncDto.base?.specialDefense
                val speed = pokemonSyncDto.base?.speed

                // Profile
                val height = pokemonSyncDto.profile.height
                val weight = pokemonSyncDto.profile.weight

                // Images
                val sprite = pokemonSyncDto.image.sprite
                val thumbnail = pokemonSyncDto.image.thumbnail
                val hires = pokemonSyncDto.image.hires

                // Types
                val types = pokemonTypesEntity.filter { it.type in pokemonSyncDto.type } as MutableList<PokemonTypeEntity>

                val pokemonEntity = PokemonEntity(
                    id = pokemonSyncDto.id,
                    name = pokemonSyncDto.name.english,
                    types = types,
                    species = pokemonSyncDto.species,
                    description = pokemonSyncDto.description,
                    hp = hp,
                    attack = attack,
                    defense = defense,
                    specialAttack = specialAttack,
                    specialDefense = specialDefense,
                    speed = speed,
                    height = height,
                    weight = weight,
                    sprite = sprite,
                    thumbnail = thumbnail,
                    hires = hires,
                    evolutions = mutableListOf<PokemonEvolutionEntity>(),
                    abilities = mutableListOf<PokemonAbilityEntity>(),
                )
                val savedPokemon = save(pokemonEntity)

                // Evolutions
                if (pokemonSyncDto.evolution.prev != null) {
                    val evolution = PokemonEvolutionEntity(
                        pokemon = savedPokemon,
                        evolutionId = pokemonSyncDto.evolution.prev[0].toLong(),
                        condition = pokemonSyncDto.evolution.prev[1],
                        isPrev = true,
                        isNext = false,
                    )
                    pokemonEvolutionRepo.save<PokemonEvolutionEntity>(evolution)
                }
                pokemonSyncDto.evolution.next?.forEach {
                    val evolution = PokemonEvolutionEntity(
                        pokemon = savedPokemon,
                        evolutionId = it[0].toLong(),
                        condition = it[1],
                        isPrev = false,
                        isNext = true
                    )
                    pokemonEvolutionRepo.save<PokemonEvolutionEntity>(evolution)
                }

                pokemonSyncDto.profile.ability.forEach {
                    val ability = PokemonAbilityEntity(
                        pokemon = savedPokemon,
                        ability = it[0],
                        isHidden = it[1].toBoolean(),
                    )
                    pokemonAbilityRepo.save<PokemonAbilityEntity>(ability)
                }
            }
        } catch (ex: Exception) {
            LOG.debug(ex.toString())
            throw Exception("Error while syncing Pokémon data: ${ex.message}")
        }
    }

    private fun fetchPokemonListFromJson(): List<PokemonSyncDto> {
        val classLoader = this::class.java.classLoader
        val resource = classLoader.getResource(JSON_FILENAME)

        if (resource != null) {
            val path = Paths.get(resource.toURI())
            val jsonContent = Files.readString(path) // Read the file contents
            return objectMapper.readValue(jsonContent) // Convert JSON to Pokemon object
        } else {
            throw RuntimeException("File not found! $JSON_FILENAME")
        }
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(PokemonService::class.java)
    }
}