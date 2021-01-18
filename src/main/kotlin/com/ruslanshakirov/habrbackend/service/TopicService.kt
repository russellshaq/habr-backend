package com.ruslanshakirov.habrbackend.service

import com.ruslanshakirov.habrbackend.dto.CreateTopicDto
import com.ruslanshakirov.habrbackend.entity.Topic
import com.ruslanshakirov.habrbackend.mappers.TopicMapper
import com.ruslanshakirov.habrbackend.repository.TopicRepository
import com.ruslanshakirov.habrbackend.util.exception.NotFoundException
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service

@Service
class TopicService(private val topicRepository: TopicRepository, private val topicMapper: TopicMapper) {
    fun create(topicDto: CreateTopicDto): Topic {
        val topic = topicMapper.toEntity(topicDto)
        return topicRepository.save(topic)
    }

    fun getById(id: Long): Topic {
        return topicRepository.findById(id).orElseThrow { NotFoundException("Topic is not found") }
    }

    fun update(topicDto: CreateTopicDto, id: Long): Topic {
        val topic = getById(id)
        topic.name = topicDto.name
        return topicRepository.save(topic)
    }

    fun remove(id: Long) {
        val topic = getById(id)
        topicRepository.delete(topic)
    }

    fun getAll(): List<Topic> {
       return topicRepository.findAll()
    }
}
