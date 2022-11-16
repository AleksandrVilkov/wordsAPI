package app.controller

import app.dto.GameDto
import app.dto.MessageDto
import app.dto.toEntity
import app.model.enumCollectilos.GameStatus
import app.model.enumCollectilos.LetterStatus
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
    fun startGame(@RequestParam userId: Int, @RequestParam countLettersInWord: Int): ResponseEntity<Any> {
        val msgs = mutableListOf<MessageDto>()
        val userGames = gameService.readUserGames(userId, msgs)
        if (!canStartGame(userGames, msgs)) {
            return ResponseEntity.status(406).build()
        }
        val game = GameDto(
            userId = userId,
            status = GameStatus.IN_GAME.name,
            countLettersInHiddenWord = countLettersInWord,
            created = LocalDate.now().toString(),
            countAttempts = 0
        )
        val result = gameService.createGame(game, msgs)
        if (msgs.isEmpty()) {
            return ResponseEntity.ok(result)
        }
        return ResponseEntity.ok(MessageDto("Error start"))
    }

    @GetMapping("/check")
    fun tryCheck(@RequestParam word: String): ResponseEntity<Any> {
        val msg = mutableListOf<MessageDto>()
        val foundWord = wordService.findWord(value = word, msg)
            ?: return ResponseEntity.status(406).build()

        return ResponseEntity.ok(foundWord)
    }


    @GetMapping("/history")
    fun getAllHistory(@RequestParam userId: Int): ResponseEntity<Any> {
        val msgs = mutableListOf<MessageDto>()

        if (userId == 0)
            msgs.add(MessageDto("userUID is empty"))

        val userGames = gameService.readUserGames(userId, msgs)
        if (msgs.isNotEmpty()) {
            return ResponseEntity.status(406).build()
        }
        val result = userGames.map {
            it.toEntity()
        }
        return ResponseEntity.ok(result)
    }

    @GetMapping("/save/win")
    fun saveDefeat(@RequestParam userId: Int, @RequestParam gameId: Int): ResponseEntity<String> {
        val msgs = mutableListOf<MessageDto>()
        val game = gameService.foundUserGameInGame(userId = userId, gameId = gameId, msgs)
            ?: return ResponseEntity.ok("Game with status IN GAME not found")
        game.status = GameStatus.FINISHED.name
        gameService.updateGames(game, msgs)
        return ResponseEntity.ok("win save")
    }

    @GetMapping("/attempt")
    fun makeAttempt(
        @RequestParam userId: Int,
        @RequestParam gameId: Int,
        @RequestParam attemptWord: String
    ): ResponseEntity<MutableMap<String, LetterStatus>>? {
        val msgs = mutableListOf<MessageDto>()
        val result = mutableMapOf<String, LetterStatus>()
        gameService.attemptResult(
            attemptWord = attemptWord,
            userId = userId,
            gameId = gameId,
            msgs = msgs,
            result = result
        )
        return ResponseEntity.ok(result)
    }
}