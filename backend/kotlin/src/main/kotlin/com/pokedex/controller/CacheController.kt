package com.pokedex.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CacheEvict
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin/cache")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Cache", description = "Operations related to cache management")
class CacheController(
    private val cacheManager: CacheManager,
) {
    @DeleteMapping("/clear-all")
    @Operation(summary = "Clear all caches", description = "Clear all caches")
    fun clearAllCache(): ResponseEntity<String> {
        cacheManager.cacheNames.forEach { name ->
            cacheManager.getCache(name)?.clear()
        }
        return ResponseEntity.ok("All caches cleared")
    }

    @DeleteMapping("/clear/{type}")
    @Operation(summary = "Clear specific cache", description = "Clear specific cache")
    fun clearSpecificCache(
        @PathVariable type: String,
    ): ResponseEntity<String> {
        val cache = cacheManager.getCache(type)

        return if (cache != null) {
            cache.clear()
            ResponseEntity.ok("Cache '$type' cleared successfully")
        } else {
            ResponseEntity.status(404).body("Cache '$type' not found")
        }
    }

    @DeleteMapping("/clear/pokemon/{id}")
    @Operation(summary = "Clear specific cache for a Pokemon", description = "Clear specific cache for a Pokemon")
    @CacheEvict(value = ["pokemon"], key = "#id")
    fun clearSpecificCache(
        @PathVariable id: Int,
    ): ResponseEntity<String> {
        val cache = cacheManager.getCache("pokemon")
        cache?.evict(id)
        return ResponseEntity.ok("Cache for Pokemon #'$id' cleared successfully")
    }
}
