package com.ruslanshakirov.habrbackend.config.handlers

import com.fasterxml.jackson.databind.ObjectMapper
import com.ruslanshakirov.habrbackend.AuthUser
import com.ruslanshakirov.habrbackend.mappers.UserMapper
import jdk.jfr.ContentType
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
@Component
class MyLoginSuccessHandler(private val mapper: ObjectMapper,
                            private val userMapper: UserMapper): AuthenticationSuccessHandler   {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val user = (authentication.principal as AuthUser).user
        val userDto = userMapper.toDto(user)
        response.characterEncoding = "UTF-8"
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        mapper.writeValue(response.outputStream, userDto)
    }
}
