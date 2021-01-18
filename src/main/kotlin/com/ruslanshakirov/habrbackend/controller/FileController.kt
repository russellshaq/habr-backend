package com.ruslanshakirov.habrbackend.controller

import com.ruslanshakirov.habrbackend.dto.FileDto
import com.ruslanshakirov.habrbackend.service.FileSaver
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Paths

@RestController
@RequestMapping("/files")
class FileController(private val fileSaver: FileSaver) {
    @Value("\${imagePath}")
    private lateinit var imagePath: String

    @Value("\${postDir}")
    private lateinit var postDir: String

    @PostMapping
    fun upload(@RequestParam file: MultipartFile): FileDto {
        val uploadPath = Paths.get(this.imagePath)
        val fileName = fileSaver.save(file, uploadPath.resolve(postDir))
        return FileDto(Paths.get(postDir).resolve(fileName).toString())
    }
}
