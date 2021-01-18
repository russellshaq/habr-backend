package com.ruslanshakirov.habrbackend.validators

import com.ruslanshakirov.habrbackend.validator.FieldsMatch
import org.springframework.beans.BeanWrapperImpl
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class FieldsMatchValidator : ConstraintValidator<FieldsMatch, Any> {
    private lateinit var message: String
    private lateinit var fieldOne: String
    private lateinit var fieldTwo: String

    override fun initialize(constraintAnnotation: FieldsMatch) {
        message = constraintAnnotation.message
        fieldOne = constraintAnnotation.fieldOne
        fieldTwo = constraintAnnotation.fieldTwo
    }

    override fun isValid(value: Any, context: ConstraintValidatorContext): Boolean {
        val propertyOne = BeanWrapperImpl(value).getPropertyValue(fieldOne)
        val propertyTwo = BeanWrapperImpl(value).getPropertyValue(fieldTwo)
        val isValid = propertyOne == propertyTwo;
        context.buildConstraintViolationWithTemplate(message)
            .addPropertyNode(fieldTwo)
            .addConstraintViolation()
            .disableDefaultConstraintViolation();
        return isValid;
    }
}
