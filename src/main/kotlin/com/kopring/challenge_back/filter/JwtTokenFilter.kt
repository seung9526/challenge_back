package com.kopring.challenge_back.filter

import com.kopring.challenge_back.service.JwtTokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtTokenFilter(private val jwtTokenProvider: JwtTokenProvider) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        // JWT 토큰을 Authorization 헤더에서 추출
        val token = resolveToken(request)

        // 토큰이 유효한 경우
        if (token != null && jwtTokenProvider.validateToken(token)) {
            val authentication = jwtTokenProvider.getAuthentication(token)
            // 인증 객체를 SecurityContext에 설정
            SecurityContextHolder.getContext().authentication = authentication
        }

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response)
    }

    // Authorization 헤더에서 Bearer 토큰을 추출하는 메서드
    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }
}
