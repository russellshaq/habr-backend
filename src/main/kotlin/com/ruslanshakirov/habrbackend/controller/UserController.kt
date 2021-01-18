package com.ruslanshakirov.habrbackend.controller

import com.ruslanshakirov.habrbackend.dto.*
import com.ruslanshakirov.habrbackend.mappers.UserMapper
import com.ruslanshakirov.habrbackend.service.UserAuthorService
import com.ruslanshakirov.habrbackend.service.UserService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController(
    private val userMapper: UserMapper,
    private val userService: UserService,
    private val userAuthorService: UserAuthorService
) {

    @PostMapping("/subscribe")
    fun subscribe(@Valid @RequestBody subscriptionDto: CreateActionDto) {
        userAuthorService.subscribe(subscriptionDto)
    }

    @GetMapping("/{id}/check-subscribed")
    fun checkSubscribed(@PathVariable id: Long): CheckActionDto {
        return userAuthorService.checkSubscribed(id)
    }

    @PutMapping("/{id}")
    fun updateProfile(@PathVariable id: Long, @Valid @RequestBody profileDto: ProfileDto): UserDto {
        val user = userService.updateProfile(profileDto, id)
        return userMapper.toDto(user)
    }

    @PostMapping("/update")
    fun updateProfile(@RequestParam(required = false) file: MultipartFile?, profileDto: ProfileDto): UserDto {
        val user = userService.updateProfile(file, profileDto)
        return userMapper.toDto(user)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): UserDto {
        val user = userService.getById(id)
        return userMapper.toDto(user)
    }
}
