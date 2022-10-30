package controllers

import controllers.entityVO.Response
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/game")
class GameController {

    @PostMapping("/win/save")
    fun saveWin(): Response {
        return Response("", "")
    }

    @PostMapping("/defeat/save")
    fun saveDefeat(): Response {
        return Response("", "")
    }

    @GetMapping("/history")
    fun getAllHistory(): Response {
        return Response("", "")
    }

    @GetMapping("/records")
    fun getRecords(): Response {
        return Response("", "")
    }
}