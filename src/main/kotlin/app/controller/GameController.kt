package app.controller

import app.dto.GameDto
import app.dto.MessageDto
import app.dto.WordDto
import app.model.enumCollectilos.GameStatus
import app.model.enumCollectilos.LetterStatus
import model.Entity.Game
import model.Entity.Message
import model.isEmptyString
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
@RequestMapping("/game")
class GameController(
    @Autowired
    val gameService: GameServiceInterface,
    @Autowired
    val wordService: WordServiceInterface
) {

    @GetMapping("/start")
    fun startGame(@RequestParam userUid: String, @RequestParam countLettersInWord: Int): ResponseEntity<Any> {
        val msgs = mutableListOf<Message>()
        val userGames = gameService.readUserGames(userUid, msgs)
        if (!canStartGame(userGames, msgs)) {
            return ResponseEntity.status(406).build()
        }
        val game = GameDto(
            userUid = userUid,
            status = GameStatus.IN_GAME.name,
            countLettersInHiddenWord = countLettersInWord,
            created = LocalDate.now().toString()
        )
        val result = gameService.createGame(game, msgs)
        if (result != null || msgs.isEmpty()) {
            return ResponseEntity.ok(result?.let {
                val gameDto = GameDto(
                    uid = it.uid,
                    created = result.created.toString(),
                    userUid = result.userUid,
                    status = result.status.name,
                    hiddenWord = result.hiddenWord
                )
                gameDto
            })
        }
        return ResponseEntity.ok(MessageDto())
    }

    @GetMapping("/check")
    fun tryCheck(@RequestParam word: String): ResponseEntity<Any> {
        val msg = mutableListOf<Message>()
        val foundWord = wordService.findWord(value = word, msg)
            ?: return ResponseEntity.status(406).build()

        return ResponseEntity.ok(WordDto(value = foundWord.wordValue))
    }


    @GetMapping("/history")
    fun getAllHistory(@RequestParam userUid: String): ResponseEntity<Any> {
        val msgs = mutableListOf<Message>()

        if (isEmptyString(userUid))
            msgs.add(Message("userUID is empty", ""))

        val userGames = gameService.readUserGames(userUid, msgs)
        if (msgs.isNotEmpty()) {
            return ResponseEntity.status(406).build()
        }
        val result = mutableListOf<GameDto>()
        for (userGame in userGames) {
            result.add(
                GameDto(
                    uid = userGame.uid,
                    created = userGame.created.toString(),
                    userUid = userGame.userUid,
                    status = userGame.status.name,
                    hiddenWord = userGame.hiddenWord
                )
            )
        }
        return ResponseEntity.ok(result)
    }

    @GetMapping("/save/win")
    fun saveDefeat(@RequestParam userUid: String, @RequestParam gameUid: String): ResponseEntity<String> {
        val msgs = mutableListOf<Message>()
        val game = gameService.foundUserGameInGame(userUid = userUid, gameUid = gameUid, msgs)
            ?: return ResponseEntity.ok("Game with status IN GAME not found")
        game.status = GameStatus.FINISHED
        gameService.updateGames(game, msgs)
        return ResponseEntity.ok("win save")
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