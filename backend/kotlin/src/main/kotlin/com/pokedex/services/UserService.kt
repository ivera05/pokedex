package com.pokedex.services

import com.pokedex.entities.UserEntity
import com.pokedex.repositories.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class UserService(
    private val userRepo: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun createUser(username: String, password: String) {
        val hashedPassword = passwordEncoder.encode(password)
        val user = UserEntity(
            username = username,
            password = hashedPassword)
        userRepo.save(user)
    }

    fun update(username: String, plainPassword: String) {
        var user = userRepo.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found: $username")

        val hashedPassword = passwordEncoder.encode(plainPassword) // Hash the password

        user.password = hashedPassword
        user.updatedAt = Instant.now()
        userRepo.save(user)
    }
}