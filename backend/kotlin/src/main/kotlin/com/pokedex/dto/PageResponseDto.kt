package com.pokedex.dto

data class PageResponseDto<T>(
    val content: List<T>,
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int,
    val sort: List<String>,
    val appliedFilters: FilterMetadata = FilterMetadata(),
)

data class FilterMetadata(
    val applied: Map<String, Any?> = emptyMap(),
)
