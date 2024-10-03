package com.pokedex.controllers

import com.pokedex.dtos.AuthRequest
import com.pokedex.dtos.AuthResponse
import com.pokedex.dtos.RefreshTokenRequest
import com.pokedex.dtos.RegistrationResponse
import com.pokedex.dtos.TokenResponse
import com.pokedex.services.AuthenticationService
import com.pokedex.services.UserService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class AuthController(
    private val authenticationService: AuthenticationService,
    private val userService: UserService,
) {

    @PostMapping("api/auth/signup")
    fun register(@Valid @RequestBody authRequest: AuthRequest): RegistrationResponse {

        LOG.debug(authRequest.toString())

        userService.createUser(authRequest.username, authRequest.password)
        return RegistrationResponse(
            success = true,
            message = "User ${authRequest.username} registered successfully!."
        )
    }

    @PostMapping("/api/auth/login")
    fun login( @RequestBody authRequest: AuthRequest): AuthResponse {
        return authenticationService.authentication(authRequest)
    }

    @PostMapping("/api/auth/refresh")
    fun refreshAccessToken(
        @RequestBody request: RefreshTokenRequest
    ): TokenResponse {
        return authenticationService.refreshAccessToken(request.token)
            ?.mapToTokenResponse()
            ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid refresh token.")
    }

//    @PostMapping("/api/auth/logout")
//    fun logout(@RequestHeader("Authorization") authHeader: String): ResponseEntity<String> {
//        val jwtToken = authHeader.substring(7)
//        jwtUtil.invalidateToken(jwtToken)
//
//        return ResponseEntity.ok("Logged out successfuly.")
//    }

    private fun String.mapToTokenResponse(): TokenResponse {
        return TokenResponse(
            token = this
        )
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(AuthController::class.java)
    }
}