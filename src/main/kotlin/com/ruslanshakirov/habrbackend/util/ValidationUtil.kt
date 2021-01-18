package com.ruslanshakirov.habrbackend.util

import com.ruslanshakirov.habrbackend.util.exception.ValidationError
import org.springframework.web.bind.MethodArgumentNotValidException
import javax.validation.ConstraintViolationException

object ValidationUtil {
    fun buildValidationErrors(ex: MethodArgumentNotValidException): List<ValidationError> {
        return ex.bindingResult.fieldErrors
            .map { ValidationError(it.objectName, it.field, it.rejectedValue, it.defaultMessage) }
    }

    fun buildValidationErrors(ex: ConstraintViolationException): List<ValidationError> {
        return ex.constraintViolations.map {
            ValidationError(
                it.rootBeanClass.name,
                it.propertyPath.toString(),
                it.invalidValue,
                it.message
            )
        }
    }
}
