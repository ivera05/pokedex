package com.pokedex.service

import com.pokedex.entity.UserEntity
import com.pokedex.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found")

        return org.springframework.security.core.userdetails.User
            .withUsername(user.username)
            .password(user.password) // Must be encoded in DB
            .authorities("USER")
            .build()
    }

    fun createUser(username: String, rawPassword: String): UserEntity {
        val encodedPassword = passwordEncoder.encode(rawPassword)
            ?: error("Password encoding returned null for user: $username")
        val newUser = UserEntity(
            username = username,
            password = encodedPassword
        )
        return userRepository.save(newUser)
    }

    fun findByUsername(username: String): UserEntity {
        return userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found.")
    }
}