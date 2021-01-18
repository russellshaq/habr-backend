package com.ruslanshakirov.habrbackend.mappers

import com.ruslanshakirov.habrbackend.dto.CommentDto
import com.ruslanshakirov.habrbackend.dto.CreateCommentDto
import com.ruslanshakirov.habrbackend.entity.Comment
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = [UserMapper::class])
interface CommentMapper {
    fun toEntity(commentDto: CreateCommentDto): Comment

    @Mappings(
        Mapping(source = "post.id", target = "postId"),
    )
    fun toDto(comment: Comment): CommentDto
}
