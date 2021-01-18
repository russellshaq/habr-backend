package com.ruslanshakirov.habrbackend.repository

import com.ruslanshakirov.habrbackend.entity.Post
import com.ruslanshakirov.habrbackend.entity.PostBookmark
import com.ruslanshakirov.habrbackend.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PostBookmarkRepository: JpaRepository<PostBookmark, Long> {
    fun findByPostAndUser(post: Post, user: User): PostBookmark?
    fun findByUserId(userId: Long, pageable: Pageable): Page<PostBookmark>
}
