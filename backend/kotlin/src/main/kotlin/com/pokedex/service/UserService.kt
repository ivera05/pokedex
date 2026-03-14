package com.pokedex.service

import com.pokedex.entity.RegionEnum
import com.pokedex.entity.UserEntity
import com.pokedex.entity.UserRole
import com.pokedex.entity.UserRoleEntity
import com.pokedex.repository.UserRepository
import com.pokedex.repository.UserRolesRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userRolesRepository: UserRolesRepository,
    private val trainerService: TrainerService,
    private val passwordEncoder: PasswordEncoder,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user =
            userRepository.findByUsername(username)
                ?: throw UsernameNotFoundException("User not found")

        return org.springframework.security.core.userdetails.User
            .withUsername(user.username)
            .password(user.password)
            .authorities("USER")
            .build()
    }

    fun createUser(
        username: String,
        rawPassword: String,
        role: UserRole? = UserRole.TRAINER,
        name: String,
        bio: String = "",
        city: String = "",
        region: RegionEnum = RegionEnum.UNKNOWN,
        avatar: String? = null,
    ): UserEntity {
        val encodedPassword =
            passwordEncoder.encode(rawPassword)
                ?: error("Password encoding returned null for user: $username")
        val newUser =
            UserEntity(
                username = username,
                password = encodedPassword,
                name = name,
                avatar = avatar?.takeIf { it.isNotBlank() } ?: "/images/avatar_default.svg",
            )
        val savedUser = userRepository.save(newUser)

        userRolesRepository.save(UserRoleEntity(0, role?.name ?: UserRole.TRAINER.name, savedUser))

        trainerService.create("Jr. Trainer", bio, city, region, savedUser)

        return savedUser
    }

    fun findByUsername(username: String): UserEntity =
        userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found.")

    @Transactional
    fun incrementTokenVersion(username: String): Long {
        val user = findByUsername(username)
        user.tokenVersion += 1
        userRepository.save(user)

        return user.tokenVersion
    }
}
