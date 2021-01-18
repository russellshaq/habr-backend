package com.ruslanshakirov.habrbackend.util.exception

import java.lang.RuntimeException

class BadRequestException(message: String): RuntimeException(message) {
}
