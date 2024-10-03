package com.pokedex.services

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.pokedex.dtos.PokemonTypeSyncDto
import com.pokedex.entities.PokemonTypeEntity
import com.pokedex.repositories.PokemonTypeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.nio.file.Files
import java.nio.file.Paths

@Service
class PokemonTypeService @Autowired constructor(
    private val pokemonTypeRepo: PokemonTypeRepository,
) {
    private val JSON_FILENAME: String = "static/pokemon_types.json"
    private val objectMapper = jacksonObjectMapper()

    fun findAll(): List<PokemonTypeEntity> {
        return pokemonTypeRepo.findAll()
    }

    fun save(pokemonTypeEntity: PokemonTypeEntity): PokemonTypeEntity {
        return pokemonTypeRepo.save<PokemonTypeEntity>(pokemonTypeEntity)
    }

    fun saveAll(pokemonList: List<PokemonTypeEntity>) {
        pokemonTypeRepo.saveAll(pokemonList)
    }

    fun sync() {
        try {
            val pokemonTypesJson: List<PokemonTypeSyncDto> = fetchJsonFile()
            val pokemonTypes: List<PokemonTypeEntity> = pokemonTypesJson.map {
                PokemonTypeEntity(
                    type = it.english,
                    effective = it.effective,
                    ineffective = it.ineffective,
                    noEffect = it.noEffect
                )
            }
            saveAll(pokemonTypes)
        } catch (ex: Exception) {
            println("Error while syncing Pokémon data: ${ex.message}")
        }
    }

    private fun fetchJsonFile(): List<PokemonTypeSyncDto> {
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