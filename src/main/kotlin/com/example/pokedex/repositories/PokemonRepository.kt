package com.example.pokedex.repositories

import com.example.pokedex.entities.PokemonEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PokemonRepository: JpaRepository<PokemonEntity, Long>