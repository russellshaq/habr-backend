package com.ruslanshakirov.habrbackend.entity

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "users_stats")
class UserStats: Serializable {
    var commentCount: Int = 0
    var postCount: Int = 0
    var bookmarkCount: Int = 0
    var subscriptionCount: Int = 0
    var subscribedCount: Int = 0
    @OneToOne(fetch = FetchType.LAZY)
    @Id
    var user: User? = null
}
