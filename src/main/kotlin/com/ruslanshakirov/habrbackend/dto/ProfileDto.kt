package com.ruslanshakirov.habrbackend.dto

import javax.validation.constraints.NotBlank

data class ProfileDto(
    val id: Long,
    @field: NotBlank
    val firstName: String,
    @field: NotBlank
    val lastName: String,
    var imageUrl: String = "avatar.jpg",
) {
}
