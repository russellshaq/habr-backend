package com.ruslanshakirov.habrbackend.validator

import com.ruslanshakirov.habrbackend.validators.FieldsMatchValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@Constraint(validatedBy = [FieldsMatchValidator::class])
annotation class FieldsMatch(
    val message: String = "Fields don't match",
    val fieldOne: String,
    val fieldTwo: String,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
) {
}
