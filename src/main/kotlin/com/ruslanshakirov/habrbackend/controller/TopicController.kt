package com.ruslanshakirov.habrbackend.controller

import com.ruslanshakirov.habrbackend.dto.CreateTopicDto
import com.ruslanshakirov.habrbackend.dto.TopicDto
import com.ruslanshakirov.habrbackend.mappers.TopicMapper
import com.ruslanshakirov.habrbackend.service.TopicService
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/topics")
class TopicController(private val topicService: TopicService, private val topicMapper: TopicMapper) {
    @PostMapping
    fun create(@Valid @RequestBody topicDto: CreateTopicDto): TopicDto {
        val topic = topicService.create(topicDto)
        return topicMapper.toDto(topic)
    }

    @PutMapping("/{id}")
    fun update(@Valid @RequestBody topicDto: CreateTopicDto, @PathVariable id: Long): TopicDto {
        val topic = topicService.update(topicDto, id)
        return topicMapper.toDto(topic)
    }

    @DeleteMapping("/{id}")
    fun remove(@PathVariable id: Long) {
        topicService.remove(id)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): TopicDto {
        val topic = topicService.getById(id)
        return topicMapper.toDto(topic)
    }

    @GetMapping
    fun getAll(): List<TopicDto>{
        return topicService.getAll().map { topicMapper.toDto(it) }
    }
}
