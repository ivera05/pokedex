package com.example.pokedex

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<PokedexApplication>().with(TestcontainersConfiguration::class).run(*args)
}
