package com.kopring.challenge_back.repository

import com.kopring.challenge_back.model.Challenge
import org.springframework.data.jpa.repository.JpaRepository

interface ChallengeRepository : JpaRepository<Challenge, Long> {
    fun findByCategory(category: String): List<Challenge>
}