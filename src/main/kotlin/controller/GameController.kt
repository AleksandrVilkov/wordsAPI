package controller

import controller.entityVO.Response
import controller.entityVO.Status
import model.service.GameServiceInterface
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Component
@RestController
@RequestMapping("/api/v1/game")
class GameController(
    @Autowired
    val gameService: GameServiceInterface
) {

    @PostMapping("/win/save")
    fun saveWin(): Response {
        return Response(Status.ERROR, "Not implemented")
    }

    @PostMapping("/defeat/save")
    fun saveDefeat(): Response {
        return Response(Status.ERROR, "Not implemented")
    }

    @GetMapping("/history")
    fun getAllHistory(): Response {
        return Response(Status.ERROR, "Not implemented")
    }

    @GetMapping("/records")
    fun getRecords(): Response {
        return Response(Status.ERROR, "Not implemented")
    }
}