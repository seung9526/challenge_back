package com.kopring.challenge_back.controller

import ChallengeCreateRequest
import com.kopring.challenge_back.dto.ApiResponse
import com.kopring.challenge_back.dto.ChallengeDTO
import com.kopring.challenge_back.service.ChallengeService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.core.Authentication
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/challenges")
@SecurityRequirement(name = "bearerAuth")

class ChallengeController(private val challengeService: ChallengeService) {

    // 챌린지 생성
    @PostMapping("/create")
    fun createChallenge(
        @RequestBody request: ChallengeCreateRequest,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<ChallengeDTO>> {
        val challenge = challengeService.createChallenge(request, authentication)
        val response = ApiResponse("success", "Challenge created successfully", challenge)
        return ResponseEntity.ok(response)
    }

    // 모든 챌린지 조회
    @GetMapping
    fun getAllChallenges(): ResponseEntity<ApiResponse<List<ChallengeDTO>>> {
        val challenges = challengeService.getAllChallenges().body!!
        val response = ApiResponse("success", "Challenges retrieved successfully", challenges)
        return ResponseEntity.ok(response)
    }

    // 챌린지 수정
    @PutMapping("/{id}")
    fun updateChallenge(
        @PathVariable id: Long,
        @RequestBody updatedChallenge: ChallengeCreateRequest
    ): ResponseEntity<ApiResponse<ChallengeDTO>> {
        val challenge = challengeService.updateChallenge(id, updatedChallenge).body
        return if (challenge != null) {
            val response = ApiResponse("success", "Challenge updated successfully", challenge)
            ResponseEntity.ok(response)
        } else {
            ResponseEntity.status(404).body(ApiResponse("error", "Challenge not found", null))
        }
    }

    // 챌린지 수정
    @DeleteMapping("/{id}")
    fun deleteChallenge(@PathVariable id: Long): ResponseEntity<ApiResponse<Any>> {
        return challengeService.deleteChallenge(id).let {
            if (it.statusCode == HttpStatus.NO_CONTENT) {
                ResponseEntity.ok(ApiResponse("success", "Challenge deleted successfully", null))
            } else {
                ResponseEntity.status(404).body(ApiResponse("error", "Challenge not found", null))
            }
        }
    }
}
