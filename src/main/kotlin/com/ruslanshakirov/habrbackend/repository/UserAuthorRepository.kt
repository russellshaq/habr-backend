package com.ruslanshakirov.habrbackend.repository

import com.ruslanshakirov.habrbackend.entity.User
import com.ruslanshakirov.habrbackend.entity.UserAuthor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserAuthorRepository: JpaRepository<UserAuthor, Long> {
    @Query("select ua from UserAuthor ua where ua.user = :user and ua.author = :author")
    fun findByUserAndByAuthor(user: User, author: User): UserAuthor?
    @Query("select ua from UserAuthor ua where ua.user = :user and ua.author.id = :authorId")
    fun findByUserAndByAuthorId(user: User, authorId: Long): UserAuthor?
    fun findByUser(user: User): List<UserAuthor>
}
