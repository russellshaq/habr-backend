package com.ruslanshakirov.habrbackend.entity

import javax.persistence.*

@Entity
@Table(name="users_subscription_authors")
data class UserAuthor(
    @Id
    @GeneratedValue
    val id: Long? = null,
    @OneToOne
    val user: User? = null,
    @OneToOne
    val author: User? = null
)
