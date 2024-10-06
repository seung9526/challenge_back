package com.kopring.challenge_back.service

import com.kopring.challenge_back.model.User
import com.kopring.challenge_back.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user: User = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User not found with email: $email")

        // UserDetails를 반환 (권한 정보 포함)
        return org.springframework.security.core.userdetails.User(
            user.email,
            user.password,
            true, true, true, true,
            listOf() // 권한 리스트 추가 가능
        )
    }
}
