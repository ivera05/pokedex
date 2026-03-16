package com.pokedex.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService {
    @Value("\${app.jwt.secret}")
    private lateinit var secret: String

    @Value("\${app.jwt.expiration-ms}")
    private var expirationMs: Long = 0

    private val signingKey: SecretKey by lazy {
        Keys.hmacShaKeyFor(secret.toByteArray())
    }

    fun generateToken(
        userId: Long,
        username: String,
        name: String,
        roles: List<String>,
        tokenVersion: Long,
    ): String =
        Jwts
            .builder()
            .subject(username)
            .claim("uid", userId)
            .claim("name", name)
            .claim("authorities", roles)
            .claim("ver", tokenVersion)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + expirationMs))
            .signWith(signingKey)
            .compact()

    fun extractUserId(token: String): Long {
        val claims = getClaimsFromToken(token)
        return (claims["uid"] as? Number)?.toLong()
            ?: throw IllegalArgumentException("Claim 'userId' is not a valid number")
    }

    fun extractTokenVersion(token: String): Long {
        val claims = getClaimsFromToken(token)
        return (claims["ver"] as? Number)?.toLong()
            ?: throw IllegalArgumentException("Claim 'tokenVersion' is not a valid number")
    }

    /**
     * Validate the token for expiration and signature.
     */
    fun validateToken(token: String): Boolean =
        try {
            val claims = getClaimsFromToken(token) // Validates the token's signature internally
            !isTokenExpired(claims)
        } catch (ex: Exception) {
            false // Token is invalid
        }

    /**
     * Check if the token is expired.
     */
    private fun isTokenExpired(claims: Claims): Boolean = claims.expiration.before(Date())

    /**
     * Extract the claims from the token.
     */
    private fun getClaimsFromToken(token: String): Claims =
        Jwts
            .parser()
            .verifyWith(signingKey)
            .build()
            .parseSignedClaims(token)
            .payload
}
