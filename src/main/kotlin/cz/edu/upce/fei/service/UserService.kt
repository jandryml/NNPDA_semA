package cz.edu.upce.fei.service

import cz.edu.upce.fei.dto.JwtResponseDto
import cz.edu.upce.fei.dto.ResponseDto
import cz.edu.upce.fei.dto.UserDto
import cz.edu.upce.fei.model.Role
import cz.edu.upce.fei.model.User
import cz.edu.upce.fei.repository.UserRepository
import cz.edu.upce.fei.security.jwt.JwtUtils
import cz.edu.upce.security.service.UserDetailsImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.bytebuddy.utility.RandomString
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.mail.internet.MimeMessage


@Service
class UserService {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var mailSender: JavaMailSender

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var encoder: PasswordEncoder

    @Autowired
    private lateinit var jwtUtils: JwtUtils

    private final val RESET_URL = "localhost:8080/api/user/reset-password?resetToken="

    fun login(userDto: UserDto): JwtResponseDto {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(userDto.username, userDto.password)
        )

        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtils.generateJwtToken(authentication)
        val userDetails = authentication.principal as UserDetailsImpl
        val role = userDetails.authorities.first().authority

        return JwtResponseDto(
            userDetails.id, userDetails.username, userDetails.email, role, jwt
        )
    }

    fun register(userDto: UserDto): ResponseDto {
        if (userRepository.existsByUsername(userDto.username)) {
            return ResponseDto("Error: Username is already taken!")
        }

        if (userRepository.existsByEmail(userDto.email)) {
            return ResponseDto("Error: Email is already in use!")
        }

        val user = userDto.toModel().apply {
            password = encoder.encode(userDto.password)
            role = Role.ROLE_USER
        }

        userRepository.save(user)
        return ResponseDto("User registered successfully!")
    }

    fun changePassword(userDto: UserDto): ResponseDto {
        try {
            val userDetails = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(userDto.username, userDto.password)
            ).principal as UserDetailsImpl

            val user = userDto.toModel().apply {
                id = userDetails.id
                username = userDetails.username
                email = userDetails.email
                password = encoder.encode(userDto.newPassword)
                role = Role.valueOf(userDetails.authorities.first().authority!!)
            }
            userRepository.save(user)
        } catch (e: AuthenticationException) {
            return ResponseDto("Invalid password!")
        }
        return ResponseDto("Password successfully changed!")
    }

    fun sendResetCode(userDto: UserDto): ResponseDto {
        val user = userRepository.findByEmail(userDto.email).orElse(null)
        return if (user == null) {
            ResponseDto("Error: User with email '${userDto.email}' doesn't exist!")
        } else {
            //send email
            val token = RandomString.make(30)
            user.resetPasswordToken = token
            userRepository.save(user)

            CoroutineScope(Dispatchers.IO).launch {
                sendEmail(user.email, RESET_URL + token)
            }

            ResponseDto("Reset code sent to email!")
        }
    }

    fun resetPassword(resetToken: String, newPassword: String): ResponseDto {
        if (resetToken.isBlank()) {
            ResponseDto("Error: This password reset token is invalid!")
        }

        val user = userRepository.findByResetPasswordToken(resetToken).orElse(null)

        return if (user == null) {
            ResponseDto("Error: This password reset token is invalid!")
        } else {
            user.apply {
                resetPasswordToken = ""
                password = encoder.encode(newPassword)
            }
            userRepository.save(user)
            ResponseDto("Password reset!")
        }
    }

    fun sendEmail(recipientEmail: String, link: String) {
        val message: MimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message)
        helper.setFrom("pda.upce@gmail.com", "NNPDA support")
        helper.setTo(recipientEmail)
        val subject = "Here's the link to reset your password"
        val content = ("<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + link
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>")
        helper.setSubject(subject)
        helper.setText(content, true)
        mailSender.send(message)
    }

    fun findById(userId: Long): User? {
        return userRepository.findById(userId).orElse(null)
    }
}
