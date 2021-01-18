package com.ruslanshakirov.habrbackend.service

import com.ruslanshakirov.habrbackend.dto.CreatePostDto
import com.ruslanshakirov.habrbackend.entity.Post
import com.ruslanshakirov.habrbackend.mappers.PostMapper
import com.ruslanshakirov.habrbackend.repository.PostRepository
import com.ruslanshakirov.habrbackend.repository.TopicRepository
import com.ruslanshakirov.habrbackend.util.AuthUtil
import com.ruslanshakirov.habrbackend.util.exception.BadRequestException
import com.ruslanshakirov.habrbackend.util.exception.NotFoundException
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.annotation.Lazy
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class PostService(
    private val postRepository: PostRepository,
    private val topicRepository: TopicRepository,
    private val userAuthorService: UserAuthorService,
    @Lazy
    private val postBookmarkService: PostBookmarkService,
    private val postMapper: PostMapper
) {

    private val BEST_VOTE_COUNT = 2

    @Transactional
    @CachePut(value = ["users"], key = "#result.id")
    fun create(postDto: CreatePostDto): Post {
        val post = postMapper.toEntity(postDto)
        post.author = AuthUtil.getCurrentUser()
        val topics = topicRepository.findAllById(postDto.topicIds)
        if (topics.size == 0) throw BadRequestException("Post must be attached to at least one topic")
        topics.forEach(post::addTopic)
        return postRepository.save(post);
    }

    @Transactional
    fun update(postDto: CreatePostDto, postId: Long): Post {
        val dbPost = getByIdAndUser(postId);
        dbPost.title = postDto.title
        dbPost.body = postDto.body
        dbPost.published = postDto.published
        return postRepository.save(dbPost);
    }

    private fun getByIdAndUser(id: Long): Post {
        return postRepository.findByIdAndAuthor(id, AuthUtil.getCurrentUser())
            ?: throw NotFoundException("Post is not found")
    }

    fun getByIdWithComments(id: Long): Post {
        return postRepository.findByIdWithComments(id) ?: throw NotFoundException("Post is not found");

    }

    @Cacheable(value = ["users"], key="id")
    fun getById(id: Long): Post {
        return postRepository.findById(id).orElseThrow { NotFoundException("Post is not found") }
    }

    @CacheEvict(value = ["users"], key = "id")
    fun remove(id: Long) {
        val post = getByIdAndUser(id)
        postRepository.delete(post)
    }

    fun getBySubscriptionAuthors(pageable: Pageable): Slice<Post> {
        val authors = userAuthorService.getByUser(AuthUtil.getCurrentUser())
        return postRepository.findByAuthorIn(authors, pageable)
    }

    fun getAllOrByTopic(topicId: Long?, pageable: Pageable): Page<Post> {
        return postRepository.findAllOrByTopic(topicId, pageable)
    }

    fun getAllByAuthor(authorId: Long, pageable: Pageable): Page<Post> {
        return postRepository.findByAuthorId(authorId, pageable);
    }

    fun getBookmarkedByUser(userId: Long, pageable: Pageable): Page<Post> {
        return postBookmarkService.getByUser(userId, pageable)
            .map { it.post!! }
    }

    fun getBestByPeriod(startDate: LocalDate, endDate: LocalDate, pageable: Pageable): Slice<Post> {
        val startDateTime = LocalDateTime.of(startDate, LocalTime.MIN)
        val endDateTime = LocalDateTime.of(endDate, LocalTime.MAX)
        return postRepository.findByCreatedAtBetweenAndVoteCountGreaterThan(
            startDateTime,
            endDateTime,
            BEST_VOTE_COUNT,
            pageable
        )
    }
}
