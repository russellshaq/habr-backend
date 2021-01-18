package com.ruslanshakirov.habrbackend.dto

import com.ruslanshakirov.habrbackend.entity.UserStats
import java.time.LocalDateTime

class PostDto(
    val id: Long,
    val title: String,
    val body: String,
    val published: Boolean,
    val voteCount: Int,
    val commentCount: Int,
    val bookmarkCount: Int,
    val comments: Set<CommentDto>?,
    val author: UserDto,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
