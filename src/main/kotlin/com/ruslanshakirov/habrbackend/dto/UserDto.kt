package com.ruslanshakirov.habrbackend.dto

import java.time.LocalDateTime

data class UserDto(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val imageUrl: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val stats: UserStatsDto
)
