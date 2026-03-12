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
        tokenVersion: Long,
    ): String =
        Jwts
            .builder()
            .subject(username)
            .claim("userId", userId)
            .claim("username", username)
            .claim("name", name)
            .claim("tokenVersion", tokenVersion)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + expirationMs))
            .signWith(signingKey)
            .compact()

    fun extractUserId(token: String): Long {
        val claims = getClaimsFromToken(token)
        return (claims["userId"] as? Number)?.toLong()
            ?: throw IllegalArgumentException("Claim 'userId' is not a valid number")
    }

    fun extractUsername(token: String): String {
        val claims = getClaimsFromToken(token)
        return (claims["username"] as? String)
            ?: throw IllegalArgumentException("Claim 'username' is not a valid")
    }

    fun extractTokenVersion(token: String): Long {
        val claims = getClaimsFromToken(token)
        return (claims["tokenVersion"] as? Number)?.toLong()
            ?: throw IllegalArgumentException("Claim 'tokenVersion' is not a valid number")
    }

    fun extractName(token: String): String {
        val claims = getClaimsFromToken(token)
        return (claims["name"] as? String)
            ?: throw IllegalArgumentException("Claim 'name' is not a valid")
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
