package com.ruslanshakirov.habrbackend.repository

import com.ruslanshakirov.habrbackend.entity.Post
import com.ruslanshakirov.habrbackend.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate
import java.time.LocalDateTime

interface PostRepository: JpaRepository<Post, Long> {
    fun findByIdAndAuthor(id: Long, author: User): Post?
    fun findByAuthorId(authorId: Long, pageable: Pageable): Page<Post>
    fun findByAuthorIn(authors: Collection<User>, pageable: Pageable): Slice<Post>
    @EntityGraph(attributePaths = ["author"])
    @Query("select distinct p from Post p join p.topics t where :topicId is null or t.id = :topicId")
    fun findAllOrByTopic(topicId: Long?, pageable: Pageable): Page<Post>
    fun findByCreatedAtBetweenAndVoteCountGreaterThan(startDate: LocalDateTime, endDate: LocalDateTime,
                                                      voteCount: Int, pageable: Pageable): Slice<Post>
    @Query("select p from Post p left join fetch p.comments where p.id = :id")
    fun findByIdWithComments(id: Long): Post?
}
