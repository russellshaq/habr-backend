package com.ruslanshakirov.habrbackend.dto

import com.ruslanshakirov.habrbackend.validator.FieldsMatch
import com.ruslanshakirov.habrbackend.validators.ValidPassword
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@FieldsMatch(fieldOne = "password", fieldTwo = "rePassword")
data class CreateUserDto(
    @field:NotBlank
    @field:Length(min = 3, max = 20)
    val firstName: String,
    @field:NotBlank
    @field:Length(min = 3, max = 20)
    val lastName: String,
    @field:NotBlank
    @field:Email
    val email: String,
    @field:NotBlank
    @field:ValidPassword
    val password: String,
    @field:NotBlank
    val rePassword: String
)
