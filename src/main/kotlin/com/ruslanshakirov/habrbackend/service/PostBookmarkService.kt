package com.ruslanshakirov.habrbackend.service

import com.ruslanshakirov.habrbackend.dto.CreateActionDto
import com.ruslanshakirov.habrbackend.entity.PostBookmark
import com.ruslanshakirov.habrbackend.repository.PostBookmarkRepository
import com.ruslanshakirov.habrbackend.util.AuthUtil
import com.ruslanshakirov.habrbackend.util.exception.BadRequestException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PostBookmarkService(
    private val postBookmarkRepository: PostBookmarkRepository,
    private val postService: PostService
) {
    fun create(postBookmarkDto: CreateActionDto) {
        val post = postService.getById(postBookmarkDto.id)
        val user = AuthUtil.getCurrentUser()
        val bookmark = postBookmarkRepository.findByPostAndUser(post, user)
        handleErrors(postBookmarkDto.value, bookmark != null)
        if (postBookmarkDto.value) {
            postBookmarkRepository.save(PostBookmark(user, post))
        } else {
            postBookmarkRepository.delete(bookmark!!)
        }
    }

    fun handleErrors(value: Boolean, exists: Boolean) {
        if (value && exists) {
            throw BadRequestException("Post is bookmarked")
        } else if (!value && !exists) {
            throw BadRequestException("Post is not bookmarked")
        }
    }

    fun getByUser(userId: Long, pageable: Pageable): Page<PostBookmark> {
        return postBookmarkRepository.findByUserId(userId, pageable)
    }
}
