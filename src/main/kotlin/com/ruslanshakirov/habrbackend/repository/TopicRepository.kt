package com.ruslanshakirov.habrbackend.repository

import com.ruslanshakirov.habrbackend.entity.Topic
import org.springframework.data.jpa.repository.JpaRepository

interface TopicRepository : JpaRepository<Topic, Long> {
}
