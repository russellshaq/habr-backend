package com.ruslanshakirov.habrbackend.dto

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CreateCommentDto(
    @field:NotBlank
    @field:Length(min = 5, max = 120)
    val body: String,
    @field:NotNull
    val postId: Long
)
