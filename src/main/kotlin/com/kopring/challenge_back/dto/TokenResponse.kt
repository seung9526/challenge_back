package com.kopring.challenge_back.dto

data class TokenResponse (
    val accessToken: String,
    val refreshToken: String,
)