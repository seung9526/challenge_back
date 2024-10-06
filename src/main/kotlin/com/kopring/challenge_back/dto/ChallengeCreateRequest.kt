package com.kopring.challenge_back.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import java.util.Date

data class ChallengeCreateRequest(
    val challengeName: String,
    val challengeDescription: String,
    val category: String,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
)
