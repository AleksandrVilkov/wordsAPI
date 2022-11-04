package controller

import controller.entityVO.GameVO
import controller.entityVO.Response
import controller.entityVO.Status
import controller.entityVO.WordVO
import model.Entity.Game
import model.Entity.GameStatus
import model.Entity.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@Component
@RestController
@RequestMapping("/api/v1/game")
class GameController(
    @Autowired
    val gameService: GameServiceInterface,
    @Autowired
    val wordService: WordServiceInterface
) {

    @PostMapping("/win/save")
    fun saveWin(): Response {
        return Response(Status.ERROR, "Not implemented")
    }

    @GetMapping("/start")
    fun startGame(@RequestParam userUid: String, @RequestParam countLettersInWord: Int): Response {
        val msgs = mutableListOf<Message>()
        val userGames = gameService.readUserGames(userUid, msgs)
        if (!canStartGame(userGames, msgs)) {
            return Response(Status.ERROR, getDescription(msgs))
        }
        val game = Game(
            userUid = userUid,
            status = GameStatus.IN_GAME,
            countLettersInHiddenWord = countLettersInWord,
            created = LocalDate.now()
        )
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

    @GetMapping("/check")
    fun tryCheck(@RequestParam word: String): Response {
        val msg = mutableListOf<Message>()
        val foundWord = wordService.findWord(value = word, msg)
            ?: return Response(Status.ERROR, "Слово не найдено!", WordVO(value = ""))

        return Response(Status.OK, "", WordVO(value = foundWord.wordValue))
    }

    @PostMapping("/defeat/save")
    fun saveDefeat(): Response {
        //  TODO
        return Response(Status.ERROR, "Not implemented")
    }

    @GetMapping("/history")
    fun getAllHistory(@RequestParam userUid: String): Response {
        val msgs = mutableListOf<Message>()
        val userGames = gameService.readUserGames(userUid, msgs)
        if (msgs.isNotEmpty()) {
            return Response(Status.ERROR, getDescription(msgs))
        }
        val result = mutableListOf<GameVO>()
        for (userGame in userGames) {
            result.add(
                GameVO(
                    uid = userGame.uid,
                    created = userGame.created.toString(),
                    userUid = userGame.userUid,
                    status = userGame.status.name,
                    hiddenWord = userGame.hiddenWord
                )
            )
        }
        TODO()
    }

    @GetMapping("/records")
    fun getRecords(): Response {
        //  TODO
        return Response(Status.ERROR, "Not implemented")
    }
}