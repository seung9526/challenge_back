package com.kopring.challenge_back.service

import ChallengeCreateRequest
import com.kopring.challenge_back.dto.ChallengeDTO
import com.kopring.challenge_back.model.Challenge
import com.kopring.challenge_back.repository.ChallengeRepository
import com.kopring.challenge_back.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class ChallengeService(
    private val challengeRepository: ChallengeRepository,
    private val userRepository: UserRepository
) {

    // 챌린지 생성
    fun createChallenge(request: ChallengeCreateRequest, authentication: Authentication): ChallengeDTO {
        // 토큰에서 사용자 정보 추출
        val user = userRepository.findByEmail(authentication.name)
            ?: throw Exception("User not found")

        // Challenge 객체 생성 후 저장
        val challenge = Challenge(
            challengeName = request.challengeName,
            challengeDescription = request.challengeDescription,
            category = request.category,
            startDate = request.startDate,
            endDate = request.endDate,
            user = user
        )

        val savedChallenge = challengeRepository.save(challenge)
        return mapToDTO(savedChallenge)
    }

    private fun mapToDTO(challenge: Challenge): ChallengeDTO {
        return ChallengeDTO(
            id = challenge.id,
            challengeName = challenge.challengeName,
            challengeDescription = challenge.challengeDescription,
            category = challenge.category,
            startDate = challenge.startDate,
            endDate = challenge.endDate
        )
    }

    // 모든 챌린지 조회
    fun getAllChallenges(): ResponseEntity<List<ChallengeDTO>> {
        val challenges = challengeRepository.findAll().map { mapToDTO(it) }
        return ResponseEntity.ok(challenges)
    }

    // 챌린지 수정
    fun updateChallenge(id: Long, updatedChallenge: ChallengeCreateRequest): ResponseEntity<ChallengeDTO> {
        val challenge = challengeRepository.findById(id)
        return if (challenge.isPresent) {
            val existingChallenge = challenge.get()
            existingChallenge.challengeName = updatedChallenge.challengeName
            existingChallenge.challengeDescription = updatedChallenge.challengeDescription
            existingChallenge.startDate = updatedChallenge.startDate
            existingChallenge.endDate = updatedChallenge.endDate
            val savedChallenge = challengeRepository.save(existingChallenge)
            ResponseEntity.ok(mapToDTO(savedChallenge))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }

    // 챌린지 삭제
    fun deleteChallenge(id: Long): ResponseEntity<Void> {
        return if (challengeRepository.existsById(id)) {
            challengeRepository.deleteById(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }
}
