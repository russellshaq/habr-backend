package com.ruslanshakirov.habrbackend.mappers

import com.ruslanshakirov.habrbackend.dto.CreateUserDto
import com.ruslanshakirov.habrbackend.dto.ProfileDto
import com.ruslanshakirov.habrbackend.dto.UserDto
import com.ruslanshakirov.habrbackend.dto.UserStatsDto
import com.ruslanshakirov.habrbackend.entity.User
import com.ruslanshakirov.habrbackend.entity.UserStats
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.ReportingPolicy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
abstract class UserMapper {
    @Autowired
    protected lateinit var encoder: PasswordEncoder

    @Mappings(
        Mapping(target = "password", expression = "java(encoder.encode(userDto.getPassword()))"),
        Mapping(target = "roles", expression = "java(Set.of(Role.USER))"),
        Mapping(target = "imageUrl", constant = "avatars/avatar.jpg"))
    abstract fun toEntity(userDto: CreateUserDto): User

    @Mapping(target = "imageUrl", expression = "java(\"/api/img/\" + user.getImageUrl())")
    abstract fun toDto(user: User): UserDto
    abstract fun toDto(stats: UserStats): UserStatsDto


}
