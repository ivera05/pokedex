package com.pokedex.services

import com.pokedex.dtos.PokemonTypeSync
import com.pokedex.entities.PokemonTypeEntity
import com.pokedex.repositories.PokemonTypeRepository
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class PokemonTypeService @Autowired constructor(
    private val objectMapper: ObjectMapper,
    private val pokemonTypeRepo: PokemonTypeRepository,
    private val webClient: WebClient
) {

    fun findAll(): List<PokemonTypeEntity> {
        return pokemonTypeRepo.findAll()
    }

    fun findByType( type: String ): PokemonTypeEntity {
        return pokemonTypeRepo.findByType(type )
    }

    fun save(pokemonTypeEntity: PokemonTypeEntity): PokemonTypeEntity {
        return pokemonTypeRepo.save<PokemonTypeEntity>(pokemonTypeEntity)
    }

    fun saveAll(pokemonList: List<PokemonTypeEntity>) {
        pokemonTypeRepo.saveAll(pokemonList)
    }

    fun sync(jsonUrl: String) {
        try {
            val pokemonTypesJson = fetchJsonFromUrl(jsonUrl)

            val pokemonTypes: List<PokemonTypeEntity> = pokemonTypesJson.map { type: PokemonTypeSync ->
                PokemonTypeEntity(
                    type = type.english,
                    effective = type.effective,
                    ineffective = type.ineffective,
                    noEffect = type.noEffect
                )
            }
            saveAll(pokemonTypes)
        } catch (ex: Exception) {
            println("Error while syncing Pokémon data: ${ex.message}")
        }
    }

    private fun fetchJsonFromUrl(url: String): List<PokemonTypeSync> {
        return webClient.get()
            .uri(url)
            .accept(MediaType.TEXT_PLAIN)
            .retrieve()
            .bodyToMono(String::class.java)
            .map { json ->
                objectMapper.readValue(json, object : TypeReference<List<PokemonTypeSync>>() {})
            }
            .block() ?: emptyList()
    }
}