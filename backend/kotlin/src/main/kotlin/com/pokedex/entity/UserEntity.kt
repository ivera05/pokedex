package com.pokedex.entity

import jakarta.persistence.*
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true, nullable = false)
    private val username: String = "",

    @Column(nullable = false)
    private val password: String = "",

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    val roles: List<UserRole> = listOf(UserRole.TRAINER)
) : UserDetails {

    override fun getUsername(): String = username
    override fun getPassword(): String = password

    override fun getAuthorities() = roles.map { SimpleGrantedAuthority(it.name) }
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
}

enum class UserRole {
    ADMIN,
    TRAINER
}