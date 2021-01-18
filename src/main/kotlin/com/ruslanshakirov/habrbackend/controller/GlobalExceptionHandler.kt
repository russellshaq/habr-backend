package com.ruslanshakirov.habrbackend.controller

import com.ruslanshakirov.habrbackend.util.ValidationUtil
import com.ruslanshakirov.habrbackend.util.exception.ApiError
import com.ruslanshakirov.habrbackend.util.exception.BadRequestException
import com.ruslanshakirov.habrbackend.util.exception.ErrorType
import com.ruslanshakirov.habrbackend.util.exception.NotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotFound(ex: MethodArgumentNotValidException, req: HttpServletRequest): ApiError {
        log.error("MethodArgumentNotValidException on request {}", req.requestURL, ex)
        val apiError = ApiError(req.requestURL.toString(), ex, ErrorType.VALIDATION)
        apiError.subErrors = ValidationUtil.buildValidationErrors(ex)
        return apiError
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException::class)
    fun notFound(ex: NotFoundException, req: HttpServletRequest): ApiError {
        log.error("NotFoundException on request {}", req.requestURL, ex)
        return ApiError(req.requestURL.toString(), ex, ErrorType.NOT_FOUND)
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun internalServer(ex: Exception, req: HttpServletRequest): ApiError {
        log.error("InternalServerException on request {}", req.requestURL, ex)
        return ApiError(req.requestURL.toString(), ex, ErrorType.APPLICATION)
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ConstraintViolationException::class)
    fun constraintViolation(ex: ConstraintViolationException, req: HttpServletRequest): ApiError {
        log.error("ConstraintViolationException on request {}", req.requestURL, ex)
        val apiError = ApiError(req.requestURL.toString(), ex, ErrorType.VALIDATION)
        apiError.subErrors = ValidationUtil.buildValidationErrors(ex)
        return apiError
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun dataIntegrityViolation(ex: DataIntegrityViolationException, req: HttpServletRequest): ApiError {
        log.error("ConstraintViolationException on request {}", req.requestURL, ex)
        return ApiError(req.requestURL.toString(), ex, ErrorType.DATA_ERROR)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException::class)
    fun badRequest(ex: BadRequestException, req: HttpServletRequest): ApiError {
        log.error("BadRequestException on request {}", req.requestURL, ex)
        return ApiError(req.requestURL.toString(), ex, ErrorType.BAD_REQUEST)
    }
}
