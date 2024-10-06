package com.kopring.challenge_back.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    var email: String = "",

    @Column(nullable = false)
    var password: String = "",

    @Column(nullable = false)
    var roles: String = "",

    // 챌린지와의 일대다 관계 설정
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var challenges: List<Challenge> = mutableListOf()
)
