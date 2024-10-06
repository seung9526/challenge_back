package com.kopring.challenge_back.service

import com.kopring.challenge_back.dto.LoginRequest
import com.kopring.challenge_back.dto.SignupRequest
import com.kopring.challenge_back.dto.TokenResponse
import com.kopring.challenge_back.exception.AuthenticationException
import com.kopring.challenge_back.model.User
import com.kopring.challenge_back.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
    ) {

    fun login(loginRequest: LoginRequest): TokenResponse {
        try {
            val authenticationToken = UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
            val authentication: Authentication = authenticationManager.authenticate(authenticationToken)
            SecurityContextHolder.getContext().authentication = authentication

            // Access Token 및 Refresh Token 생성
            val accessToken = jwtTokenProvider.createToken(loginRequest.username, authentication.authorities.map { it.authority })
            val refreshToken = jwtTokenProvider.createRefreshToken(loginRequest.username)

            // TokenResponse에 AccessToken과 RefreshToken을 포함해서 반환
            return TokenResponse(accessToken, refreshToken)
        } catch (e: Exception) {
            throw AuthenticationException("Invalid username or password")
        }
    }

    fun signup(signupRequest: SignupRequest) {
        if (userRepository.existsByEmail(signupRequest.email)) {
            throw IllegalArgumentException("Email already exists")
        }

        val user = User(
            email = signupRequest.email,
            password = passwordEncoder.encode(signupRequest.password),
            roles = "ROLE_USER"
        )
        userRepository.save(user)
    }
}
