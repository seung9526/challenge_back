package com.kopring.challenge_back.config

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.media.Schema
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springdoc.core.customizers.OpenApiCustomizer
import org.springdoc.core.models.GroupedOpenApi
import java.time.*
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
    fun openApiCustomizer(): OpenApiCustomizer {
        return OpenApiCustomizer { openApi ->
            openApi.components.schemas.forEach { (_, schema) ->
                schema.properties?.forEach { (key, value) ->
                    if (key.contains("startDate") || key.contains("endDate")) {
                        // 날짜 형식 강제 설정
                        value.example = "2024-10-06 10:11"
                    }
                }
            }
        }
    }

    @Bean
    fun openApi(): OpenAPI {
        return OpenAPI().components(
            Components().schemas(
                mapOf(
                    "ChallengeCreateRequest" to Schema<Any>().apply {
                        properties = mapOf(
                            "challengeName" to Schema<Any>().apply {
                                example = "하루 일기"
                                type = "string"
                            },
                            "challengeDescription" to Schema<Any>().apply {
                                example = "습관들이기"
                                type = "string"
                            },
                            "category" to Schema<Any>().apply {
                                example = "일기"
                                type = "string"
                            },
                            "startDate" to Schema<Any>().apply {
                                example = "2024-10-06 10:11"
                                type = "string"
                                pattern = "yyyy-MM-dd HH:mm"
                            },
                            "endDate" to Schema<Any>().apply {
                                example = "2024-11-06"
                                type = "string"
                                pattern = "yyyy-MM-dd"
                            }
                        )
                    }
                )
            )
        )
    }
}