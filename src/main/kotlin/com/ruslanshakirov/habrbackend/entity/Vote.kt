package com.ruslanshakirov.habrbackend.entity

import javax.persistence.*

@Entity
@Table(name = "votes")
data class Vote(
    @Id
    @GeneratedValue
    val id: Long? = null,
    var value: Boolean = true,
    @ManyToOne
    var post: Post? = null,
    @ManyToOne
    var user: User? = null
)
