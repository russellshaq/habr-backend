package com.ruslanshakirov.habrbackend.mappers

import com.ruslanshakirov.habrbackend.dto.CreateTopicDto
import com.ruslanshakirov.habrbackend.dto.TopicDto
import com.ruslanshakirov.habrbackend.entity.Topic
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface TopicMapper {
    fun toEntity(topicDto: CreateTopicDto): Topic
    fun toDto(topic: Topic): TopicDto
}
