package com.ruslanshakirov.habrbackend.dto

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotBlank

data class CreateTopicDto(
    @field: NotBlank
    @field: Length(min = 3)
    val name: String
)
