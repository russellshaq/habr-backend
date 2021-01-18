package com.ruslanshakirov.habrbackend.service

import com.ruslanshakirov.habrbackend.dto.CreateCommentDto
import com.ruslanshakirov.habrbackend.entity.Comment
import com.ruslanshakirov.habrbackend.entity.Post
import com.ruslanshakirov.habrbackend.mappers.CommentMapper
import com.ruslanshakirov.habrbackend.repository.CommentRepository
import com.ruslanshakirov.habrbackend.util.AuthUtil
import com.ruslanshakirov.habrbackend.util.exception.NotFoundException
import com.ruslanshakirov.habrbackend.util.exception.DisabledException
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val postService: PostService,
    private val userService: UserService,
    private val commentMapper: CommentMapper
) {
    fun create(commentDto: CreateCommentDto): Comment {
        val post = postService.getById(commentDto.postId)
        checkPostPublished(post)
        val comment = commentMapper.toEntity(commentDto)
        comment.post = post
        comment.user = userService.getById(AuthUtil.getCurrentUserId())
        return commentRepository.save(comment)
    }

    fun update(commentDto: CreateCommentDto, commentId: Long): Comment {
        val comment = get(commentId)
        checkPostPublished(comment.post!!)
        comment.body = commentDto.body
        return commentRepository.save(comment)
    }

    fun getByPost(postId: Long): Slice<Comment> {
       return commentRepository.findByPostId(postId)
    }

    fun get(id: Long): Comment {
        return commentRepository.findByIdAndUser(id, AuthUtil.getCurrentUser())
            ?: throw NotFoundException("Comment is not found")
    }

    fun remove(id: Long) {
        val comment = get(id)
        commentRepository.delete(comment)
    }

    private fun checkPostPublished(post: Post) {
        if (!post.published) {
            throw DisabledException("Post is not published")
        }
    }
}
