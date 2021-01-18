package com.ruslanshakirov.habrbackend.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "comments")
data class Comment(
    @Id
    @GeneratedValue
    val id: Long? = null,
    var body: String? = null,
    @ManyToOne
    var post: Post? = null,
    @ManyToOne
    var user: User? = null,
    @CreationTimestamp
    var createdAt: LocalDateTime? = null,
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null
) {
}
