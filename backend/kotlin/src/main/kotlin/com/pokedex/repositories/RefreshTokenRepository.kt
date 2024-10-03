package com.pokedex.repositories

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
class RefreshTokenRepository {
    private val tokens = mutableMapOf<String, UserDetails>()

    fun findUserDetailsByToken(token: String) : UserDetails? {
        return tokens[token]
    }

    fun save(token: String, userDetails: UserDetails) {
        tokens[token] = userDetails
    }
}