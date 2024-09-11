package com.example.pokedex.repositories

import com.example.pokedex.entities.PokemonTypeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PokemonTypeRepository: JpaRepository<PokemonTypeEntity, Long>