package com.pokedex.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource


@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = getTokenFromRequest(request)
        if (!token.isNullOrEmpty() && jwtService.validateToken(token)) {
            val username = jwtService.extractUsername(token) // You'll need to add this method to JwtService
            val authorities = emptyList<SimpleGrantedAuthority>() // Add logic to get roles if needed

            val authentication = UsernamePasswordAuthenticationToken(
                username, // Principal
                null,     // Credentials (don't store the token here)
                authorities
            )
            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }

    private fun getTokenFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }
}