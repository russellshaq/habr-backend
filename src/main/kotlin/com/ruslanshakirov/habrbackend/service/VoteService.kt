package com.ruslanshakirov.habrbackend.service

import com.ruslanshakirov.habrbackend.dto.CreateActionDto
import com.ruslanshakirov.habrbackend.dto.CreateVoteDto
import com.ruslanshakirov.habrbackend.entity.User
import com.ruslanshakirov.habrbackend.entity.Vote
import com.ruslanshakirov.habrbackend.mappers.VoteMapper
import com.ruslanshakirov.habrbackend.repository.VoteRepository
import com.ruslanshakirov.habrbackend.util.AuthUtil
import com.ruslanshakirov.habrbackend.util.exception.BadRequestException
import org.springframework.stereotype.Service

@Service
class VoteService(
    private val voteRepository: VoteRepository, private val postService: PostService, private val voteMapper: VoteMapper
) {
    fun create(voteDto: CreateActionDto): Vote {
        val user = AuthUtil.getCurrentUser();
        checkAlreadyVoted(voteDto.id, user);
        val vote = voteMapper.toEntity(voteDto)
        vote.post = postService.getById(voteDto.id)
        vote.user = user
        return voteRepository.save(vote);
    }

    private fun checkAlreadyVoted(postId: Long, user: User) {
        val vote = voteRepository.findByPostIdAndUser(postId, user);
        if (vote != null) {
            throw BadRequestException("You already voted")
        }
    }
}
