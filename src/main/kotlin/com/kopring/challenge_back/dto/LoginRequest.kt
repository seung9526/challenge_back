package com.kopring.challenge_back.dto

import jakarta.validation.constraints.Email

data class LoginRequest(
    val email: String,
    val password: String
)