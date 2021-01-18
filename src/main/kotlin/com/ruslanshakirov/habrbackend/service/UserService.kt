package com.ruslanshakirov.habrbackend.service

import com.ruslanshakirov.habrbackend.AuthUser
import com.ruslanshakirov.habrbackend.dto.CreateUserDto
import com.ruslanshakirov.habrbackend.dto.ProfileDto
import com.ruslanshakirov.habrbackend.dto.UserDto
import com.ruslanshakirov.habrbackend.entity.User
import com.ruslanshakirov.habrbackend.entity.UserStats
import com.ruslanshakirov.habrbackend.mappers.UserMapper
import com.ruslanshakirov.habrbackend.repository.UserRepository
import com.ruslanshakirov.habrbackend.util.AuthUtil
import com.ruslanshakirov.habrbackend.util.exception.NotFoundException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val fileSaver: FileSaver,
    private val userMapper: UserMapper,
) : UserDetailsService {
    @Value("\${imagePath}")
    private lateinit var imagePath: String

    @Value("\${avatarDir}")
    private lateinit var avatarDir: String

    fun create(userDto: CreateUserDto): User {
        val user = userMapper.toEntity(userDto)
        user.addStats(UserStats())
        val dbUser = userRepository.save(user)
        AuthUtil.authenticate(userDto.email, userDto.password)
        return dbUser
    }

    override fun loadUserByUsername(email: String?): UserDetails {
        val user = email?.let { userRepository.findByEmail(it) }
            ?: throw UsernameNotFoundException("User is not found")
        return AuthUser(user)
    }

    fun getById(id: Long): User {
        return userRepository.findById(id)
            .orElseThrow { NotFoundException("User is not found") }
    }

    fun updateProfile(profile: ProfileDto, id: Long): User {
        val user = getById(id)
        user.firstName = profile.firstName
        user.lastName = profile.lastName
        user.imageUrl = profile.imageUrl
        return userRepository.save(user)
    }

    fun updateProfile(file: MultipartFile?, profileDto: ProfileDto): User {
        if (file != null) {
            val uploadPath = Paths.get(this.imagePath)
            val fileName = fileSaver.save(file, uploadPath.resolve(avatarDir))
            profileDto.imageUrl = Paths.get(avatarDir).resolve(fileName).toString()
        }
        return updateProfile(profileDto, profileDto.id)
    }
}
