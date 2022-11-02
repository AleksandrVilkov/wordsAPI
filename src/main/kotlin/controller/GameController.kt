package controller

import controller.entityVO.GameVO
import controller.entityVO.Response
import controller.entityVO.Status
import model.Entity.Game
import model.Entity.GameStatus
import model.Entity.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*

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

    @GetMapping("/start")
    fun startGame(@RequestParam userUid: String, @RequestParam countLettersInWord: Int): Response {
        val game = Game(
            userUid = userUid,
            status = GameStatus.IN_GAME,
            countLettersInHiddenWord = countLettersInWord
        )
        val msgs = mutableListOf<Message>()
        val result = gameService.createGame(game, msgs)
        if (result != null || msgs.isEmpty()) {
            return Response(Status.OK, "", result?.let {
                val gameVO = GameVO(
                    uid = it.uid,
                    created = result.created.toString(),
                    userUid = result.userUid,
                    status = result.status.name,
                    hiddenWord = result.hiddenWord
                )
                gameVO
            })
        }
        return Response(Status.ERROR, getDescription(msgs))
    }

    @PostMapping("/check")
    fun tryCheck() {
        //TODO
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