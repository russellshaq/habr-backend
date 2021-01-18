package com.ruslanshakirov.habrbackend.repository

import com.ruslanshakirov.habrbackend.entity.Comment
import com.ruslanshakirov.habrbackend.entity.User
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {
    fun findByIdAndUser(id: Long, user: User): Comment?
    fun findByPostId(postId: Long): Slice<Comment>
}
