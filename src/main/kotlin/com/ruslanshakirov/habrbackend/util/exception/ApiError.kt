package com.ruslanshakirov.habrbackend.util.exception

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.apache.commons.lang3.exception.ExceptionUtils
import java.time.LocalDateTime
@JsonPropertyOrder("timestamp", "type", "url", "message")
class ApiError(
    val url: String,
    ex: Exception,
    val type: ErrorType
) {
    val message : String = ExceptionUtils.getRootCauseMessage(ex)
    val timestamp: LocalDateTime = LocalDateTime.now()
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var subErrors: List<SubError>? = null

}
