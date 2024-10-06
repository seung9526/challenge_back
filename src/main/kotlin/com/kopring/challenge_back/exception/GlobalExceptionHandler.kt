package com.kopring.challenge_back.exception

import com.kopring.challenge_back.dto.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse<Any?>> {
        val errorResponse = ApiResponse<Any?>(
            status = "error",
            message = e.message ?: "An unexpected error occurred",
            data = null
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(e: AuthenticationException): ResponseEntity<ApiResponse<Any?>> {
        val errorResponse = ApiResponse<Any?>(
            status = "error",
            message = e.message ?: "Authentication failed",
            data = null
        )
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse)
    }
}