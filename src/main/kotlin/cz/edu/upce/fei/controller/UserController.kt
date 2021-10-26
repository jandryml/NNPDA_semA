package cz.edu.upce.fei.controller

import cz.edu.upce.fei.dto.JwtResponseDto
import cz.edu.upce.fei.dto.ResponseDto
import cz.edu.upce.fei.dto.UserDto
import cz.edu.upce.fei.service.UserService
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
@Api( tags = ["User"])
class UserController(
    @Autowired val userService: UserService
) {

    @PostMapping("/login")
    fun login(@RequestBody userDto: UserDto): ResponseEntity<JwtResponseDto> {
        val jwtResponse = userService.login(userDto)
        return ResponseEntity.ok()
            .headers(HttpHeaders().apply { set("authorization", "Bearer ${jwtResponse.accessToken}") })
            .body(jwtResponse)
    }

    @PostMapping("/register")
    fun register(@RequestBody userDto: UserDto): ResponseEntity<ResponseDto> {
        return ResponseEntity.ok(userService.register(userDto))
    }

    @PostMapping("/forgot-password")
    fun forgotPassword(@RequestBody userDto: UserDto): ResponseEntity<ResponseDto> {
        return ResponseEntity.ok(userService.sendResetCode(userDto))
    }

    @PostMapping("/reset-password")
    fun resetPassword(
        @RequestBody newPassword: String,
        @RequestParam(name = "resetToken") resetToken: String
    ): ResponseEntity<ResponseDto> {
        return ResponseEntity.ok(userService.resetPassword(resetToken, newPassword))
    }

    @PostMapping("/change-password")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    fun changePassword(@RequestBody userDto: UserDto): ResponseEntity<ResponseDto> {
        return ResponseEntity.ok(userService.changePassword(userDto))
    }
}
