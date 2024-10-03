package com.pokedex.services

import com.pokedex.configuration.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.Date

@Service
class TokenService (
    jwtProperties: JwtProperties
) {
    private val secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun generate(
        userDetails: UserDetails,
        expirationDate: Instant,
        additionalClaims: Map<String, Any> = emptyMap()
    ): String {
        return Jwts.builder()
            .setClaims(additionalClaims)
            .setSubject(userDetails.username)
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(Date.from(expirationDate))
            .signWith(secretKey)
            .compact()
    }

    fun isValid(token: String, userDetails: UserDetails): Boolean {
        val user = extractUser(token)
        return userDetails.username == user && !isExpired(token)
    }

    fun extractUser(token: String ): String? {
        return getAllClaims(token).subject
    }

    fun isExpired( token: String ): Boolean {
        return getAllClaims(token = token)
            .expiration
            .before(Date.from(Instant.now()))
    }

    private fun getAllClaims(token: String): Claims {
        val parser = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()

        return parser.parseClaimsJws(token).body
    }
}