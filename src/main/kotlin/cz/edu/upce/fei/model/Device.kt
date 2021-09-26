package cz.edu.upce.fei.model

import cz.edu.upce.fei.dto.DeviceDto
import javax.persistence.*

@Entity(name = "device")
class Device(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = Long.MIN_VALUE,
    var name: String = "",
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    val user: User? = null
) {
    fun toDto() = DeviceDto(id, name, user?.id ?: Long.MIN_VALUE)
}