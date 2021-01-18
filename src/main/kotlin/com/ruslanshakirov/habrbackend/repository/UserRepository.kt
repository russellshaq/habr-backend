package com.ruslanshakirov.habrbackend.repository

import com.ruslanshakirov.habrbackend.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository: JpaRepository<User, Long>{
    fun findByEmail(email: String):User?
}
