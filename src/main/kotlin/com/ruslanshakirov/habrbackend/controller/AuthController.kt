package com.ruslanshakirov.habrbackend.controller

import com.ruslanshakirov.habrbackend.dto.CreateUserDto
import com.ruslanshakirov.habrbackend.dto.UserDto
import com.ruslanshakirov.habrbackend.mappers.UserMapper
import com.ruslanshakirov.habrbackend.service.UserService
import com.ruslanshakirov.habrbackend.util.AuthUtil
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController(private val userService: UserService, private val userMapper: UserMapper) {

    @PostMapping("/signup")
    fun signup(@Valid @RequestBody userDto: CreateUserDto): UserDto {
        val user = userService.create(userDto);
        return userMapper.toDto(user);
    }

    @GetMapping("/check")
    fun checkAuthenticated(): UserDto {
        val user = userService.getById(AuthUtil.getCurrentUserId())
        return userMapper.toDto(user);
    }
}
