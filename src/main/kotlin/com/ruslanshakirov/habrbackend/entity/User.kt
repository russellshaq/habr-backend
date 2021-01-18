package com.ruslanshakirov.habrbackend.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue
    val id: Long? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var password: String? = null,
    var enabled: Boolean = true,
    var imageUrl: String? = null,
    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var stats: UserStats? = null,
    @CreationTimestamp
    val createdAt: LocalDateTime? = null,
    @UpdateTimestamp
    val updatedAt: LocalDateTime? = null,
    @CollectionTable(name = "users_roles", joinColumns = [JoinColumn(name = "user_id")])
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    val roles: MutableSet<Role> = HashSet()
) {
    fun addRole(role: Role) {
        roles.add(role)
    }

    fun addStats(stats: UserStats) {
        stats.user = this
        this.stats = stats
    }
}
