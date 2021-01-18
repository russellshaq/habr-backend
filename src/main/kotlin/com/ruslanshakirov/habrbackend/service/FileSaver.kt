package com.ruslanshakirov.habrbackend.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

@Component
class FileSaver {
    fun save(file: MultipartFile, path: Path): String {
        if (Files.notExists(path)) {
            Files.createDirectory(path)
        }
        val fileName = "${UUID.randomUUID()}_${file.originalFilename}"
        file.transferTo(path.resolve(fileName))
        return fileName
    }
}
