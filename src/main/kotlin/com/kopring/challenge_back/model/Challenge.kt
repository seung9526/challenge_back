package com.kopring.challenge_back.model

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "challenges")
data class Challenge(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var challengeName: String = "",  // 챌린지 이름

    @Column(nullable = false)
    var challengeDescription: String = "",  // 챌린지 설명

    @Column(nullable = false)
    var category: String = "",  // 챌린지를 구분하는 토큰 필드 추가

    @Column(nullable = false)
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    var startDate: LocalDateTime = LocalDateTime.now(),  // 챌린지 시작 날짜

    @Column(nullable = false)
    @CreationTimestamp
    var endDate: LocalDateTime = LocalDateTime.now().plusDays(30),  // 챌린지 종료 날짜

    // 사용자와 다대일 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User
)
