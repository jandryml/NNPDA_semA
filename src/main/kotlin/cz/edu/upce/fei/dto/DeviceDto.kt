package cz.edu.upce.fei.dto

import cz.edu.upce.fei.model.Device
import cz.edu.upce.fei.model.User
import javax.validation.constraints.NotBlank

class DeviceDto(
    var id: Long = Long.MIN_VALUE,
    var name: @NotBlank String = "",
    var userId: Long = Long.MIN_VALUE
) {
    fun toModel(getUser: (id: Long) -> User?) = Device(id, name, getUser(userId))
}