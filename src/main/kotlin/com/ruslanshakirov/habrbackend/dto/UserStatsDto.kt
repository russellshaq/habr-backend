package com.ruslanshakirov.habrbackend.dto

import java.io.Serializable
import javax.persistence.*

class UserStatsDto(
    val commentCount: Int,
    val postCount: Int,
    val bookmarkCount: Int,
    val subscriptionCount: Int,
    val subscribedCount: Int
)
