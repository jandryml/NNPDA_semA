package cz.edu.upce.fei.dto

import cz.edu.upce.fei.model.Role
import cz.edu.upce.fei.model.User

class UserDto(
    var id: Long = Long.MIN_VALUE,
    var username: String = "",
    var email: String = "",
    var password: String = "",
    val role: String = Role.ROLE_USER.name,
    val newPassword: String = ""
) {
    fun toModel() = User(id, username, email, password, Role.valueOf(role))
}