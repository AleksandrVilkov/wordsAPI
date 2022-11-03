package controller

import controller.entityVO.AuthRequest
import model.Entity.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import security.jwt.JwtTokenProvider

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    @Autowired
    val authManager: AuthenticationManager,
    @Autowired
    val jwtTokenProvider: JwtTokenProvider,
    @Autowired
    val userService: UserServiceInterface
) {

    @PostMapping("/login")
    fun login(@RequestBody authRequest: AuthRequest): ResponseEntity<MutableMap<String, String>> {
        try {
            val login = authRequest.login
            authManager.authenticate(UsernamePasswordAuthenticationToken(login, authRequest.pass))
            val msgs = mutableListOf<Message>()
            val user = userService.findUser(login, msgs) ?: throw Exception("User not found")
            val token = jwtTokenProvider.createToken(login, mutableListOf(user.role))
            val response = mutableMapOf<String, String>()
            response["login"] = login
            response["token"] = token
            return ResponseEntity.ok(response)
        } catch (e: Exception) {
            throw BadCredentialsException("Invalid login or password")
        }
    }
}