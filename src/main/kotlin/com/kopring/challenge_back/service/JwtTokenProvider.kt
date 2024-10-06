package com.kopring.challenge_back.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.io.Decoders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(private val userDetailsService: UserDetailsService) {

    // Base64로 인코딩된 시크릿 키
    private val base64EncodedSecretKey = "fzWQa1Smrj10TWtyIXL3f+anMfOnv4Lr4/Nqe6Y6q9Y="
    private val accessTokenValidity = 3600000L  // 1시간
    private val refreshTokenValidity = 604800000L  // 7일 (밀리초로 설정)

    // SecretKey 생성
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64EncodedSecretKey))

    // Access Token 생성
    fun createToken(email: String, roles: List<String>): String {
        val claims: Claims = Jwts.claims().setSubject(email)
        claims["roles"] = roles

        val now = Date()
        val validity = Date(now.time + accessTokenValidity)

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    // Refresh Token 생성
    fun createRefreshToken(email: String): String {
        val now = Date()
        val validity = Date(now.time + refreshTokenValidity)

        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    // 토큰에서 사용자명(전화번호) 추출
    fun getUsername(token: String): String? {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }

    // 토큰 유효성 검사
    fun validateToken(token: String): Boolean {
        return try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
            !claims.body.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }

    // 토큰을 기반으로 Authentication 객체를 생성하는 메서드
    fun getAuthentication(token: String): Authentication {
        // 토큰에서 사용자 이름(전화번호)을 추출
        val username = getUsername(token) ?: throw IllegalArgumentException("Invalid token")

        // UserDetailsService를 통해 사용자 정보 로드
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(username)

        // 사용자 정보를 포함한 UsernamePasswordAuthenticationToken 반환
        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }
}
