package controller

import controller.entityVO.Response
import controller.entityVO.Status
import controller.entityVO.UserVO
import model.service.UserServiceInterface
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*

@Component
@RestController
@RequestMapping("/api/v1/user")
class UserController(
    @Autowired
    val userService: UserServiceInterface
) {

    @GetMapping("/check/{login}")
    fun checkUser(@PathVariable login: String): Response {
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

    @PostMapping("/save")
    fun saveUser(): Response {
        return Response(Status.ERROR, "Not implemented")
    }
}