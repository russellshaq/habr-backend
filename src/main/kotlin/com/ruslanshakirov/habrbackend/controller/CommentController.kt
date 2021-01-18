package com.ruslanshakirov.habrbackend.controller

import com.ruslanshakirov.habrbackend.dto.CommentDto
import com.ruslanshakirov.habrbackend.dto.CreateCommentDto
import com.ruslanshakirov.habrbackend.mappers.CommentMapper
import com.ruslanshakirov.habrbackend.service.CommentService
import org.springframework.data.domain.Slice
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comments")
class CommentController(private val commentService: CommentService, private val commentMapper: CommentMapper) {

    @PostMapping
    fun create(@RequestBody commentDto: CreateCommentDto): CommentDto {
        val comment = commentService.create(commentDto)
        return commentMapper.toDto(comment)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody commentDto: CreateCommentDto): CommentDto {
        val comment = commentService.update(commentDto, id)
        return commentMapper.toDto(comment)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun remove(@PathVariable id: Long) {
        commentService.remove(id);
    }

    @GetMapping("/by-post")
    fun getByPost(@RequestParam postId: Long): Slice<CommentDto> {
        return commentService.getByPost(postId).map{commentMapper.toDto(it)}
    }
}
