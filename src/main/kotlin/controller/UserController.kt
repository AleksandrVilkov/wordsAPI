package controller

import controller.entityVO.Response
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user")
class UserController {
    @PostMapping("/check")
    fun checkUser(): Response {
        return Response("", "")
    }
    @PostMapping("/save")
    fun saveUser(): Response {
        return Response("", "")
    }
    @GetMapping("/user/")
    fun getUserInfo(): Response {
        return Response("", "")
    }

}