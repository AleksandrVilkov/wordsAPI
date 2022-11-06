package controller

import controller.entityVO.GameVO
import controller.entityVO.Response
import controller.entityVO.Status
import controller.entityVO.WordVO
import model.Entity.Game
import model.Entity.GameStatus
import model.Entity.LetterStatus
import model.Entity.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.lang.RuntimeException
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

    @GetMapping("/start")
    fun startGame(@RequestParam userUid: String, @RequestParam countLettersInWord: Int): ResponseEntity<Response> {
        val msgs = mutableListOf<Message>()
        val userGames = gameService.readUserGames(userUid, msgs)
        if (!canStartGame(userGames, msgs)) {
            return ResponseEntity.ok(Response(Status.ERROR, getDescription(msgs)))
        }
        val game = Game(
            userUid = userUid,
            status = GameStatus.IN_GAME,
            countLettersInHiddenWord = countLettersInWord,
            created = LocalDate.now()
        )
        val result = gameService.createGame(game, msgs)
        if (result != null || msgs.isEmpty()) {
            return ResponseEntity.ok(Response(Status.OK, "", result?.let {
                val gameVO = GameVO(
                    uid = it.uid,
                    created = result.created.toString(),
                    userUid = result.userUid,
                    status = result.status.name,
                    hiddenWord = result.hiddenWord
                )
                gameVO
            }))
        }
        return ResponseEntity.ok(Response(Status.ERROR, getDescription(msgs)))
    }

    @GetMapping("/check")
    fun tryCheck(@RequestParam word: String): Response {
        val msg = mutableListOf<Message>()
        val foundWord = wordService.findWord(value = word, msg)
            ?: return Response(Status.ERROR, "Слово не найдено!", WordVO(value = ""))

        return Response(Status.OK, "", WordVO(value = foundWord.wordValue))
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

//    @GetMapping("/randomWord")
//    fun getRandomWord(@RequestParam countLetters: Int): Response {
//        val msgs = mutableListOf<Message>()
//        val correctCountLetters = checkCount(countLetters)
//        val word = wordService.findRandomWord(countLetters, msgs)
//        if (msgs.isEmpty() && correctCountLetters) {
//            Response(Status.OK, "", word?.let { WordVO(it.wordValue) })
//        }
//        return Response(Status.ERROR, getDescription(msgs))
//    }

    @GetMapping("/records")
    fun getRecords(): Response {
        //  TODO
        return Response(Status.ERROR, "Not implemented")
    }

    @GetMapping("/save/win")
    fun saveDefeat(@RequestParam userUid: String, @RequestParam gameUid: String): Response {
        val msgs = mutableListOf<Message>()
        val game = gameService.foundUserGameInGame(userUid = userUid, gameUid = gameUid, msgs)
            ?: return Response(Status.ERROR,"Game with status IN GAME not found")
        game.status = GameStatus.FINISHED
        gameService.updateGames(game, msgs)
        return Response(Status.OK, "win save")
    }

    @GetMapping("/attempt")
    fun makeAttempt(
        @RequestParam userUid: String,
        @RequestParam gameUid: String,
        @RequestParam attemptWord: String
    ): ResponseEntity<MutableMap<String, LetterStatus>>? {
        val msgs = mutableListOf<Message>()
        val result = mutableMapOf<String, LetterStatus>()
        gameService.attemptResult(
            attemptWord = attemptWord,
            userUid = userUid,
            gameUid = gameUid,
            msgs = msgs,
            result = result
        )
        return ResponseEntity.ok(result)
    }
}