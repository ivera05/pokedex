package com.pokedex.repositories

import com.pokedex.entities.PokemonEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PokemonRepository: JpaRepository<PokemonEntity, Long> {

}