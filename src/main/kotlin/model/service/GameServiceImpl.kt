package model.service

import controller.GameServiceInterface
import controller.WordServiceInterface
import jdk.jshell.Snippet
import logger.Logger
import model.Entity.Game
import model.Entity.GameStatus
import model.Entity.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class GameServiceImpl(
    @Autowired
    private val dbConnector: DataBaseConnector,
    private val wordService: WordServiceInterface
) : GameServiceInterface {
    val logger = Logger("GameServiceImpl")

    override fun createGame(game: Game, msgs: MutableList<Message>): Game? {
        val word = wordService.findRandomWord(game.countLettersInHiddenWord, msgs)
        game.hiddenWord = word?.wordValue.toString()
        if (game.hiddenWord.isEmpty()) {
            val messageTest = "Ошибка получения случайного слова длинной ${game.countLettersInHiddenWord}"
            msgs.add(
                Message(messageTest, "")
            )
            logger.debug(messageTest)
            game.status = GameStatus.ERROR
        }
        return if (dbConnector.save(game)) {
            game
        } else {
            val text = "Не удалось создать игру для полльзователя  ${game.userUid}"
            msgs.add(Message(text, ""))
            logger.debug(text)
            null
        }
    }

    override fun readUserGames(userUid: String, msgs: MutableList<Message>): List<Game> {
        val result = dbConnector.read(dbConnector.getProperties().getProperty("gamesTable"), "useruid", "'$userUid'")
        val games = mutableListOf<Game>()
        while (result.next()) {
            games.add(
                Game(
                    created = LocalDate.parse(result.getString("created")),
                    userUid = result.getString("useruid"),
                    updated = LocalDate.parse(result.getString("updated")),
                    status = defineGameStatus(result.getString("status")),
                    time = result.getString("time"),
                    hiddenWord = result.getString("hiddenWord"),
                    countLettersInHiddenWord = result.getString("hiddenWord").length,
                    countAttempts = result.getInt("countAttempts")
                )
            )
        }
        return games
    }

    override fun updateGames(gameUid: String, msgs: MutableList<Message>): Boolean {
        TODO("Not yet implemented")
    }


    private fun defineGameStatus(statusString: String): GameStatus {
        return when (statusString) {
            GameStatus.IN_GAME.name -> GameStatus.IN_GAME
            GameStatus.NOT_IN_GAME.name -> GameStatus.NOT_IN_GAME
            GameStatus.ERROR.name -> GameStatus.ERROR
            GameStatus.FINISHED.name -> GameStatus.FINISHED
            else -> GameStatus.NOT_DETERMINED
        }
    }
}