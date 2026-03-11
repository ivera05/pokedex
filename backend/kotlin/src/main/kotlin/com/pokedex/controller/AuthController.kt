package com.pokedex.controller

import com.pokedex.dto.AuthResponse
import com.pokedex.dto.LoginRequest
import com.pokedex.dto.RegisterRequest
import com.pokedex.security.JwtService
import com.pokedex.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Operations related to user authentication and registration")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService,
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder
) {
    private val logger: Logger = LoggerFactory.getLogger(AuthController::class.java)

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Create a new user account.")
    fun register(@RequestBody request: RegisterRequest): String {
        userService.createUser(
            request.username,
            request.password,
            request.role,
            request.name,
            request.avatar
        )
        return "User registered successfully!"
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate a user", description = "Authenticate a user and generate a JWT token.")
    fun login(@RequestBody request: LoginRequest): AuthResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.username, request.password)
        )

        val user = userService.findByUsername(request.username)
        logger.debug(
            "Password matches for username='{}': {}", request.username,
            passwordEncoder.matches(request.password, user.password)
        )

        val token = jwtService.generateToken(user.id, request.username, user.name, user.tokenVersion)
        return AuthResponse(token)
    }

    @PostMapping("/logout")
    @Operation(
        summary = "Logout everywhere",
        description = "Invalidate all JWTs by incrementing the user's token version."
    )
    fun logout(): String {
        val username = SecurityContextHolder.getContext().authentication?.name
            ?: return "Not authenticated"

        userService.incrementTokenVersion(username)
        return "User logged out successfully!"
    }
}
