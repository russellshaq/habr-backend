package com.ruslanshakirov.habrbackend.service

import com.ruslanshakirov.habrbackend.dto.CheckActionDto
import com.ruslanshakirov.habrbackend.dto.CreateActionDto
import com.ruslanshakirov.habrbackend.entity.User
import com.ruslanshakirov.habrbackend.entity.UserAuthor
import com.ruslanshakirov.habrbackend.repository.UserAuthorRepository
import com.ruslanshakirov.habrbackend.util.AuthUtil
import com.ruslanshakirov.habrbackend.util.exception.BadRequestException
import org.springframework.stereotype.Service

@Service
class UserAuthorService(
    private val userAuthorRepository: UserAuthorRepository,
    private val userService: UserService
) {
    fun getByUser(user: User): List<User> {
        return userAuthorRepository.findByUser(user).map { it.author!! }
    }

    fun subscribe(subscriptionDto: CreateActionDto) {
        if (subscriptionDto.id == AuthUtil.getCurrentUserId()) {
            throw BadRequestException("You can't subscribe to yourself")
        }
        val author = userService.getById(subscriptionDto.id)
        val user = AuthUtil.getCurrentUser()
        val userAuthor = userAuthorRepository.findByUserAndByAuthor(user, author)
        handleErrors(subscriptionDto.value, userAuthor);
        if (subscriptionDto.value) {
            userAuthorRepository.save(UserAuthor(null, user, author))
        } else {
            userAuthorRepository.delete(userAuthor!!);
        }
    }

    fun checkSubscribed(authorId: Long): CheckActionDto {
        val userAuthor = userAuthorRepository.findByUserAndByAuthorId(AuthUtil.getCurrentUser(), authorId)
        return CheckActionDto(authorId, userAuthor != null)
    }

    private fun handleErrors(value: Boolean, userAuthor: UserAuthor?) {
        val exists = userAuthor != null
        if (value && exists) {
            throw BadRequestException("Author is already in subscription list")
        } else if (!value && !exists) {
            throw BadRequestException("Author is not in subscription list")
        }
    }
}
