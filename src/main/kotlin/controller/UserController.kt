package controller

import controller.entityVO.Response
import controller.entityVO.Status
import controller.entityVO.UserVO
import model.Entity.User
import model.Entity.UserRole
import model.Entity.UserStatus
import model.service.UserServiceInterface
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@Component
@RestController
@RequestMapping("/api/v1/user")
class UserController(
    @Autowired
    val userService: UserServiceInterface
) {

    @GetMapping("/check")
    fun checkUser(@RequestParam login: String): Response {
        val user = userService.findUser(login)
        return if (user != null) {
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
            return Response(Status.ERROR, "Login $login not found")
        }
    }

    @GetMapping("/save")
    fun saveUser(@RequestParam login: String, @RequestParam pass: String): Response {
        userService.registerUser(User(
            login = login,
            pass = pass,
            role = UserRole.ROLE_USER,
            status = UserStatus.ACTIVE,
            created = LocalDate.now()
        ))
        return Response(Status.ERROR, "Not implemented111")
    }
}