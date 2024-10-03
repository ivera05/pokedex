package com.pokedex.services

import com.pokedex.configuration.JwtProperties
import com.pokedex.dtos.AuthRequest
import com.pokedex.dtos.AuthResponse
import com.pokedex.repositories.RefreshTokenRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class AuthenticationService(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties
) {

    fun authentication(authRequest: AuthRequest): AuthResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authRequest.username,
                authRequest.password
            )
        )

        val user = userDetailsService.loadUserByUsername( authRequest.username)
        val accessToken = createAccessToken(user)
        val refreshToken = createRefreshToken(user)

        return AuthResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
    }

    fun refreshAccessToken(refreshToken: String): String? {
        val extractedUsername = tokenService.extractUser(refreshToken)

        return extractedUsername?.let { username ->
            val currentUserDetails: UserDetails = userDetailsService.loadUserByUsername(username)
            val refreshTokenUserDetails: UserDetails? = refreshTokenRepository.findUserDetailsByToken(token = refreshToken)

            if(!tokenService.isExpired(refreshToken) && refreshTokenUserDetails?.username == currentUserDetails.username) {
                createAccessToken(currentUserDetails)
            } else {
                null
            }
        }
    }

    private fun createRefreshToken(user: UserDetails): String {
        return tokenService.generate(
            userDetails = user,
            expirationDate = getRefreshTokenExpiration()
        )
    }

    private fun createAccessToken(user: UserDetails): String {
        return tokenService.generate(
            userDetails = user,
            expirationDate = getAccessTokenExpiration()
        )
    }

    private fun getAccessTokenExpiration(): Instant {
        return Instant.now().plusMillis(jwtProperties.accessTokenExpiration)
    }

    private fun getRefreshTokenExpiration(): Instant {
        return Instant.now().plusMillis(jwtProperties.refreshTokenExpiration)
    }

}