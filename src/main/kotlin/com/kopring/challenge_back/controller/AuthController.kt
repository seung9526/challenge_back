package com.kopring.challenge_back.controller

import com.kopring.challenge_back.dto.ApiResponse
import com.kopring.challenge_back.dto.LoginRequest
import com.kopring.challenge_back.dto.SignupRequest
import com.kopring.challenge_back.dto.TokenResponse
import com.kopring.challenge_back.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<ApiResponse<TokenResponse>> {
        return try {
            val tokenResponse = authService.login(loginRequest)

            val response = ApiResponse(
                status = "success",
                message = "Login successful",
                data = tokenResponse
            )
            ResponseEntity.ok(response)
        } catch (e: Exception) {
            val errorResponse = ApiResponse<TokenResponse> (
                status = "error",
                message = e.message ?: "Invalid login credentials",
                data = null
            )
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse)
        }
    }

    @PostMapping("/signup")
    fun signup(@RequestBody signupRequest: SignupRequest): ResponseEntity<ApiResponse<Any>> {
        return try {
            authService.signup(signupRequest)

            val response = ApiResponse<Any>(
                status = "success",
                message = "Signup successful",
                data = null
            )
            ResponseEntity.status(HttpStatus.CREATED).body(response)
        } catch (e: Exception) {
            val errorResponse = ApiResponse<Any>(
                status = "error",
                message = e.message ?: "Signup failed",
                data = null
            )
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
        }
    }
}