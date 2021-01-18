package com.ruslanshakirov.habrbackend.mappers

import com.ruslanshakirov.habrbackend.dto.CreatePostDto
import com.ruslanshakirov.habrbackend.dto.PostDto
import com.ruslanshakirov.habrbackend.dto.UserStatsDto
import com.ruslanshakirov.habrbackend.entity.Post
import com.ruslanshakirov.habrbackend.entity.UserStats
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = [UserMapper::class])
interface PostMapper {

    fun toEntity(postDto: CreatePostDto): Post

    fun toDto(post: Post): PostDto

}
