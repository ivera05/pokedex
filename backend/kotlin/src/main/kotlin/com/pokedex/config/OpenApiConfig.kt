package com.pokedex.config

import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {
    @Bean
    fun openAPI(): OpenAPI {
        val bearerSchemaName = "BearerAuth"

        return OpenAPI()
            .info(
                Info()
                    .title("Pokedex API")
                    .version("1.0.0")
                    .description("API documentation for the Pokedex backend.")
            )
            .components(
                io.swagger.v3.oas.models.Components().addSecuritySchemes(
                    bearerSchemaName,
                    SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                )
            )
            .addSecurityItem(SecurityRequirement().addList(bearerSchemaName))
    }
}
