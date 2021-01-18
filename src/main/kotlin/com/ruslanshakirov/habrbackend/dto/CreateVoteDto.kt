package com.ruslanshakirov.habrbackend.dto

import javax.validation.constraints.NotNull

data class CreateVoteDto(
    val value: Boolean = true,
    @field: NotNull
    val postId: Long
)
