package com.ruslanshakirov.habrbackend.util

import com.ruslanshakirov.habrbackend.AuthUser
import com.ruslanshakirov.habrbackend.entity.User
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY
import javax.servlet.http.HttpServletRequest
import org.springframework.web.context.request.RequestContextHolder

import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.ServletException


object AuthUtil {
    fun getCurrentUser(): User {
        val authUser = SecurityContextHolder.getContext().authentication.principal as AuthUser
        return authUser.user;
    }

    fun getCurrentUserId(): Long {
        return getCurrentUser().id!!
    }

    fun authenticate(email: String, password: String) {
        val attributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
        try {
            attributes.request.login(email, password)
        } catch (e: ServletException) {}
    }
}
