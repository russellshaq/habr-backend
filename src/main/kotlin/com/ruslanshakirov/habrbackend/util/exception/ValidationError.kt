package com.ruslanshakirov.habrbackend.util.exception

class ValidationError(
    val obj: String,
    val field: String,
    val rejectedValue: Any?,
    val message: String?
) : SubError()
