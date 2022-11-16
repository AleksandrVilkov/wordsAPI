package app.controller

import app.dto.MessageDto
import app.dto.UserDto
import app.model.enumCollectilos.UserRole
import app.model.enumCollectilos.UserStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@Component
@RestController
@RequestMapping("/user")
class UserController(
    @Autowired val userService: UserServiceInterface
) {

    @GetMapping("/check")
    fun checkUser(@RequestParam login: String): ResponseEntity<Any> {
        val msgs = mutableListOf<MessageDto>()
        val user = userService.findUser(login, msgs)
        return if (user != null && msgs.isEmpty()) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.status(406).build()
        }
    }

    @GetMapping("/save")
    fun saveUser(
        @RequestParam userId: Int,
        @RequestParam login: String,
        @RequestParam pass: String
    ): ResponseEntity<Any> {
        val msgs = mutableListOf<MessageDto>()
        userService.registerUser(
            UserDto(
                id = userId,
                login = login,
                pass = pass,
                role = UserRole.USER.name,
                status = UserStatus.ACTIVE.name,
                created = LocalDate.now().toString()
            ), msgs
        )
        if (msgs.isEmpty()) {
            return ResponseEntity.ok("user will be saved. User login: $login")
        }
        return ResponseEntity.status(500).build()
    }
}