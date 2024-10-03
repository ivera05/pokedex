package com.pokedex.filters

import com.pokedex.services.CustomUserDetailsService
import com.pokedex.services.TokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper

@Component
class JwtAuthenticationFilter(
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val cachedBodyRequest = ContentCachingRequestWrapper(request)
        val authHeader: String? = cachedBodyRequest.getHeader("Authorization")
        LOG.debug("is authHeader null?: ${(authHeader != null).toString()}")
        LOG.debug("Is bearer in header?: ${authHeader.containsBearerToken().toString()}")

        if (authHeader != null && authHeader.containsBearerToken()) {

            val jwt = authHeader.extractTokenValue()
            val userName = tokenService.extractUser(jwt)

            if( userName != null && SecurityContextHolder.getContext().authentication == null){
                val foundUser = userDetailsService.loadUserByUsername(userName)

                if (tokenService.isValid(token = jwt, userDetails = foundUser)) {
                    updateContext(foundUser, cachedBodyRequest)
                }

            }
        }

        filterChain.doFilter(request, response)
    }

    private fun String?.containsBearerToken(): Boolean {
        return this != null && this.startsWith("Bearer ")
    }

    private fun String.extractTokenValue(): String {
        return this.substringAfter("Bearer ")
    }

    private fun updateContext( user: UserDetails, request: HttpServletRequest) {
        val authToken = UsernamePasswordAuthenticationToken(user, null, user.authorities)
        authToken.details = WebAuthenticationDetailsSource().buildDetails((request))
        SecurityContextHolder.getContext().authentication = authToken
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(JwtAuthenticationFilter::class.java)
    }
}