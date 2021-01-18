package com.ruslanshakirov.habrbackend.repository

import com.ruslanshakirov.habrbackend.entity.User
import com.ruslanshakirov.habrbackend.entity.Vote
import org.springframework.data.jpa.repository.JpaRepository

interface VoteRepository : JpaRepository<Vote, Long> {
    fun findByPostIdAndUser(postId: Long, user: User): Vote?
}
