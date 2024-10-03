package com.pokedex.services

import com.pokedex.entities.UserEntity
import com.pokedex.repositories.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepo: UserRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return userRepo.findByUsername(username)
            ?.mapToUserDetails()
            ?: throw UsernameNotFoundException("Not found!")
    }

    private fun UserEntity.mapToUserDetails(): UserDetails {
        return User.builder()
            .username(this.username)
            .password(this.password)
            .roles(this.role.name)
            .build()

    }
}