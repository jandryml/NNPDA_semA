package cz.edu.upce.fei.model

import javax.persistence.*

@Entity(name = "password_code")
class PasswordResetCode(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = Long.MIN_VALUE,
    val code: String = "",
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    val user: User? = null
)