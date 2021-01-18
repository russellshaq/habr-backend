package com.ruslanshakirov.habrbackend.entity

import javax.persistence.*

@Entity
@Table(name = "topics")
data class Topic(
    @Id
    @GeneratedValue
    val id: Long? = null,
    var name: String? = null,
    val postCount: Int = 0
)
