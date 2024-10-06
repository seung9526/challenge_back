package com.kopring.challenge_back.config

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springdoc.core.customizers.OpenApiCustomizer
import org.springdoc.core.models.GroupedOpenApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Configuration
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
class SwaggerConfig {

    @Bean
    fun publicApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("v1")
            .pathsToMatch("/**")
            .build()
    }

    @Bean
    fun openApiCustomiser(): OpenApiCustomizer {
        return OpenApiCustomizer { openApi ->
            openApi.components.schemas.forEach { (_, schema) ->
                schema.properties?.forEach { (propName, prop) ->
                    if (prop.format == "date-time") {
                        // Swagger에 표시될 예시 시간값을 KST로 설정
                        prop.example = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                            .format(LocalDateTime.now().plusHours(9)) // UTC -> KST 변환
                    }
                }
            }
        }
    }
}
