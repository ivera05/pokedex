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
import org.springframework.beans.factory.annotation.Autowired
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

    fun findTopByHp(limit: Int?): List<PokemonDto> {
        return pokemonRepo.findTopByHp( limit = limit ).map { it.toPokemonDto() }
    }

    fun findTopByAttack(limit: Int?): List<PokemonDto> {
        return pokemonRepo.findTopByAttack( limit = limit ).map { it.toPokemonDto() }
    }

    fun findTopByDefense(limit: Int?): List<PokemonDto> {
        return pokemonRepo.findTopByDefense( limit = limit ).map { it.toPokemonDto() }
    }

    fun findTopBySpecialAttack(limit: Int?): List<PokemonDto> {
        return pokemonRepo.findTopBySpecialAttack( limit = limit ).map { it.toPokemonDto() }
    }

    fun findTopBySpecialDefense(limit: Int?): List<PokemonDto> {
        return pokemonRepo.findTopBySpecialDefense( limit = limit ).map { it.toPokemonDto() }
    }

    fun findTopBySpeed(limit: Int?): List<PokemonDto> {
        return pokemonRepo.findTopBySpeed( limit = limit ).map { it.toPokemonDto() }
    }

    fun findAll(cursor: Int?, limit: Int?): List<PokemonDto> {
        return pokemonRepo
            .findAll( cursor = cursor, limit = limit )
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

                val pokemonEntity = PokemonEntity(
                    id = pokemonSyncDto.id,
                    name = pokemonSyncDto.name.english,
                    types = pokemonTypesEntity.filter { it.type in pokemonSyncDto.type },
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
                    evolutions = listOf<PokemonEvolutionEntity>(),
                    abilities = listOf<PokemonAbilityEntity>(),
                )
                val savedPokemonEntity = save(pokemonEntity)

                // Evolutions
                if (pokemonSyncDto.evolution.prev != null) {

                    val evolution = PokemonEvolutionEntity(
                        pokemon = savedPokemonEntity,
                        evolutionId = pokemonSyncDto.evolution.prev[0].toLong(),
                        condition = pokemonSyncDto.evolution.prev[1],
                        isPrev = true,
                        isNext = false,
                    )
                    pokemonEvolutionRepo.save<PokemonEvolutionEntity>(evolution)
                }
                pokemonSyncDto.evolution.next?.forEach {
                    val evolution = PokemonEvolutionEntity(
                        pokemon = savedPokemonEntity,
                        evolutionId = it[0].toLong(),
                        condition = it[1],
                        isPrev = false,
                        isNext = true
                    )
                    pokemonEvolutionRepo.save<PokemonEvolutionEntity>(evolution)
                }

                pokemonSyncDto.profile.ability.forEach {
                    val ability = PokemonAbilityEntity(
                        pokemon = savedPokemonEntity,
                        ability = it[0],
                        isHidden = it[1].toBoolean(),
                    )
                    pokemonAbilityRepo.save<PokemonAbilityEntity>(ability)
                }
            }
        } catch (ex: Exception) {
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
}