package app.controller

import app.controller.entityVO.Response
import app.controller.entityVO.Status
import app.controller.entityVO.UserVO
import app.model.enumCollectilos.UserRole
import app.model.enumCollectilos.UserStatus
import model.Entity.Message
import model.Entity.User
import org.apache.catalina.filters.AddDefaultCharsetFilter
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
    @Autowired
    val userService: UserServiceInterface
) {

    @GetMapping("/check")
    fun checkUser(@RequestParam login: String): ResponseEntity<Any> {
        val msgs = mutableListOf<Message>()
        val user = userService.findUser(login, msgs)
        return if (user != null && msgs.isEmpty()) {
            ResponseEntity.ok(
                UserVO(
                    login = user.login,
                    created = user.created.toString(),
                    status = user.status.toString(),
                    role = user.role.toString()
                )
            )
        } else {
            return ResponseEntity.status(406).build()
        }
    }

    @GetMapping("/save")
    fun saveUser(@RequestParam login: String, @RequestParam pass: String): ResponseEntity<Any> {
        val msgs = mutableListOf<Message>()
        userService.registerUser(
            User(
                login = login,
                pass = pass,
                role = UserRole.USER,
                status = UserStatus.ACTIVE,
                created = LocalDate.now()
            ), msgs
        )
        if (msgs.isEmpty()) {
            return ResponseEntity.ok("user will be saved. User login: $login")
        }
        return ResponseEntity.status(500).build()
    }
}