package com.pokedex.configuration

import com.pokedex.repositories.UserRepository
import com.pokedex.services.CustomUserDetailsService
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class JwtConfiguration{

    @Bean
    fun  userDetailsService( userRepository: UserRepository): UserDetailsService{
        return CustomUserDetailsService(userRepository)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationProvider( userRepository: UserRepository): AuthenticationProvider {
        return DaoAuthenticationProvider()
            .also { auth ->
                auth.setUserDetailsService(userDetailsService((userRepository)))
                auth.setPasswordEncoder(passwordEncoder())
            }
    }

    @Bean
    fun authenticationManager( config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }
}