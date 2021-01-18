package com.ruslanshakirov.habrbackend.dto

import javax.validation.constraints.NotNull

data class CreateActionDto(
    @field: NotNull
    val id: Long,
    val value: Boolean = true
)
