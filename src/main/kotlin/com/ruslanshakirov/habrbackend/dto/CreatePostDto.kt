package com.ruslanshakirov.habrbackend.dto

import com.ruslanshakirov.habrbackend.entity.Topic
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

data class CreatePostDto(
    @field: NotBlank
    @field:Length(min = 3)
    val title: String,
    @field: NotBlank
    @field: Length(min = 3)
    val body: String,
    val published: Boolean = true,
    @field: NotEmpty
    @field: Size(max = 5)
    val topicIds: MutableSet<Long>
)
