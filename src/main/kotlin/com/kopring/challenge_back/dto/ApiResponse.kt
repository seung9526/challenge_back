package com.kopring.challenge_back.dto

data class ApiResponse<T>(
    val status: String,
    val message: String,
    val data: T? = null
)