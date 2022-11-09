package app.controller

import app.controller.entityVO.Response
import app.controller.entityVO.Status
import app.controller.entityVO.UserVO
import model.Entity.Message
import model.Entity.User
import app.model.enumCollectilos.UserRole
import app.model.enumCollectilos.UserStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@Component
@RestController
@RequestMapping("/user")
class UserController(
    @Autowired
    val userService: UserServiceInterface
) {

    @GetMapping("/check")
    fun checkUser(@RequestParam login: String): Response {
        val msgs = mutableListOf<Message>()
        val user = userService.findUser(login, msgs)
        return if (user != null && msgs.isEmpty()) {
            Response(
                Status.OK,
                "",
                UserVO(
                    login = user.login,
                    status = user.status.name,
                    created = user.created.toString(),
                    role = user.role.name
                )
            )
        } else {
            return Response(Status.ERROR, "Login $login not found \n${getDescription(msgs)}")
        }
    }

    @GetMapping("/save")
    fun saveUser(@RequestParam login: String, @RequestParam pass: String): Response {
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
            return Response(Status.OK, "user will be saved. User login: $login")
        }
        return Response(Status.ERROR, getDescription(msgs))
    }
}