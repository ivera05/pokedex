package com.pokedex.utils

import com.pokedex.dto.PageResponseDto
import org.springframework.data.domain.Page

fun <T : Any> Page<T>.toPageResponse(): PageResponseDto<T> =
    PageResponseDto(
        content = content,
        page = number,
        size = size,
        totalElements = totalElements,
        totalPages = totalPages,
        sort = sort.map { "${it.property},${it.direction.name.lowercase()}" }.toList()
    )
