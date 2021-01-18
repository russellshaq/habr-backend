package com.ruslanshakirov.habrbackend.validators

import org.passay.*
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class ValidPasswordValidator : ConstraintValidator<ValidPassword, String> {
    override fun isValid(password: String, context: ConstraintValidatorContext): Boolean {
        val charRule = CharacterCharacteristicsRule(
            3,
            CharacterRule(EnglishCharacterData.LowerCase),
            CharacterRule(EnglishCharacterData.UpperCase),
            CharacterRule(EnglishCharacterData.Digit)
        )

        val validator = PasswordValidator(
            LengthRule(5, 10),
            charRule
        )

        val validate = validator.validate(PasswordData(password))
        return validate.isValid
    }
}
