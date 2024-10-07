package com.kopring.challenge_back.model

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.media.SchemaProperty
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.usertype.internal.ZonedDateTimeCompositeUserType.ZonedDateTimeEmbeddable
import org.springframework.format.annotation.DateTimeFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

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

    @Schema(
        description = "챌린지 시작 시간",
        example = "2024-10-06 10:11",  // Swagger에서 보일 예시 값
        pattern = "yyyy-MM-dd HH:mm",
        type = "string"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    var startDate: LocalDateTime = LocalDateTime.now(),

    var endDate: LocalDate = LocalDate.now(),

    // 사용자와 다대일 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User
)
