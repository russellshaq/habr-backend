package com.ruslanshakirov.habrbackend.dto

import java.time.LocalDateTime

data class CommentDto(
    var id: Long,
    var body: String,
    var postId: Long,
    var user: UserDto,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime
)
