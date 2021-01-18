package com.ruslanshakirov.habrbackend.mappers

import com.ruslanshakirov.habrbackend.dto.CreateActionDto
import com.ruslanshakirov.habrbackend.dto.CreateVoteDto
import com.ruslanshakirov.habrbackend.entity.Vote
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface VoteMapper {
    fun toEntity(voteDto: CreateActionDto): Vote
}
