package com.ruslanshakirov.habrbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HabrBackendApplication

fun main(args: Array<String>) {
    runApplication<HabrBackendApplication>(*args)
}
