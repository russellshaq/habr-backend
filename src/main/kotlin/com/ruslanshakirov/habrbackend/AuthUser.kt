package com.ruslanshakirov.habrbackend

import com.ruslanshakirov.habrbackend.entity.User


class AuthUser(val user: User): org.springframework.security.core.userdetails.User(
    user.email,
    user.password,
    user.enabled,
    true,
    true,
    true,
    user.roles
) {
}
