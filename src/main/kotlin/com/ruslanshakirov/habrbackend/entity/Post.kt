package com.ruslanshakirov.habrbackend.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "posts")
class Post {
    @Id
    @GeneratedValue
    var id: Long? = null
    var title: String? = null
    var body: String? = null
    var published: Boolean = false
    var voteCount: Int = 0
    var commentCount: Int = 0
    var bookmarkCount: Int = 0

    @ManyToMany
    @JoinTable(
        name = "posts_topics", joinColumns = [JoinColumn(name = "post_id")],
        inverseJoinColumns = [JoinColumn(name = "topic_id")]
    )
    var topics: MutableSet<Topic>? = HashSet()

    @OneToOne(fetch = FetchType.LAZY)
    var author: User? = null

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    @OrderBy("id")
    var comments: MutableSet<Comment>? = null

    @CreationTimestamp
    var createdAt: LocalDateTime? = null;

    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null;

    fun addTopic(topic: Topic) {
        topics?.add(topic)
    }

}
