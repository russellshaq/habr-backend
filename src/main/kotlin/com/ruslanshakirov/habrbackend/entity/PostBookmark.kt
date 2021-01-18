package com.ruslanshakirov.habrbackend.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "post_bookmarks")
data class PostBookmark(
    @Id
    @GeneratedValue
    var id: Long? = null,
    @ManyToOne
    var user: User? = null,
    @ManyToOne
    var post: Post? = null,
    @CreationTimestamp
    var createdAt: LocalDateTime? = null,
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null
) {
    constructor(user: User, post: Post):this(null, user, post, null, null){

    }
}
