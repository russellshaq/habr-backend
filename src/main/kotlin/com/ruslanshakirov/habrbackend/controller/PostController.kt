package com.ruslanshakirov.habrbackend.controller

import com.ruslanshakirov.habrbackend.dto.CreateActionDto
import com.ruslanshakirov.habrbackend.dto.CreatePostDto
import com.ruslanshakirov.habrbackend.dto.PostDto
import com.ruslanshakirov.habrbackend.mappers.PostMapper
import com.ruslanshakirov.habrbackend.service.PostBookmarkService
import com.ruslanshakirov.habrbackend.service.PostService
import com.ruslanshakirov.habrbackend.service.VoteService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import javax.validation.Valid

@RestController
@RequestMapping("/posts")
class PostController(
    private val postService: PostService,
    private val postMapper: PostMapper,
    private val voteService: VoteService,
    private val bookmarkService: PostBookmarkService
) {
    @PostMapping
    fun create(@Valid @RequestBody postDto: CreatePostDto): PostDto {
        val post = postService.create(postDto)
        return postMapper.toDto(post)
    }

    @PostMapping("/vote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun vote(@Valid @RequestBody voteDto: CreateActionDto) {
        voteService.create(voteDto)
    }

    @PostMapping("/bookmark")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun bookmark(@Valid @RequestBody bookmarkDto: CreateActionDto) {
        bookmarkService.create(bookmarkDto)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody postDto: CreatePostDto): PostDto {
        val post = postService.update(postDto, id)
        return postMapper.toDto(post)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        postService.remove(id)
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): PostDto {
        val post = postService.getByIdWithComments(id)
        return postMapper.toDto(post)
    }

    @GetMapping("/subscribed")
    fun getBySubscriptionAuthors(@PageableDefault pageable: Pageable): Slice<PostDto> {
        return postService.getBySubscriptionAuthors(pageable).map { postMapper.toDto(it) }
    }

    @GetMapping("/bookmarked")
    fun getBookmarkedByUser(@RequestParam userId: Long, @PageableDefault pageable: Pageable): Page<PostDto> {
        return postService.getBookmarkedByUser(userId, pageable).map { postMapper.toDto(it) }
    }

    @GetMapping
    fun getAllOrByTopic(
        @RequestParam(required = false) topicId: Long?,
        @PageableDefault(sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable
    ): Page<PostDto> {
        return postService.getAllOrByTopic(topicId, pageable)
            .map { postMapper.toDto(it) }
    }

    @GetMapping("/by-author")
    fun getAllByAuthor(@RequestParam authorId: Long, @PageableDefault pageable: Pageable): Page<PostDto> {
        return postService.getAllByAuthor(authorId,pageable).map { postMapper.toDto(it) }
    }

    @GetMapping("/best")
    fun getBestByPeriod(
        @RequestParam(defaultValue = "2000-01-01") startDate: LocalDate,
        @RequestParam(defaultValue = "3000-12-12") endDate: LocalDate, @PageableDefault pageable: Pageable
    ): Slice<PostDto> {
        return postService.getBestByPeriod(startDate, endDate, pageable).map { postMapper.toDto(it) }
    }
}
