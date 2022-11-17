package app.controller

import app.dto.AuthRequestDto
import app.dto.MessageDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import app.security.jwt.JwtTokenProvider
import model.defineUserRole

@RestController
@RequestMapping("/auth")
class AuthController(
    @Autowired
    val authManager: AuthenticationManager,
    @Autowired
    val jwtTokenProvider: JwtTokenProvider,
    @Autowired
    val userService: UserServiceInterface
) {

    @PostMapping("/login")
    fun login(@RequestBody authRequest: AuthRequestDto): ResponseEntity<MutableMap<String, String>> {
        try {
            val login = authRequest.login
            authManager.authenticate(UsernamePasswordAuthenticationToken(login, authRequest.pass))
            val msgs = mutableListOf<MessageDto>()
            val user = userService.findUser(login, msgs) ?: throw Exception("User not found")
            if (!BCrypt.checkpw(authRequest.pass, user.pass)) {
                throw BadCredentialsException(("Invalid password"))
            }
            val token = jwtTokenProvider.createToken(login, mutableListOf(defineUserRole(user.role)))
            val response = mutableMapOf<String, String>()
            response["login"] = login
            response["token"] = token
            return ResponseEntity.ok(response)
        } catch (e: Exception) {
            throw BadCredentialsException("Invalid login or password")
        }
    }
}