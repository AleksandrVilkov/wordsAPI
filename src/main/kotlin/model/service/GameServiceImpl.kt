package model.service

import logger.Logger
import model.Entity.Game
import model.Entity.GameStatus
import model.Entity.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class GameServiceImpl(
    @Autowired
    private val dbConnector: DataBaseConnector,
    private val wordService: WordServiceInterface
) : GameServiceInterface {
    val logger = Logger("GameServiceImpl")

    override fun createGame(game: Game, msgs: MutableList<Message>): Boolean {
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
        return dbConnector.save(game)
    }

    override fun readUserGames(userUid: String, msgs: MutableList<Message>): List<Game> {
        TODO("Not yet implemented")
    }

    override fun updateGames(gameUid: String, msgs: MutableList<Message>): Boolean {
        TODO("Not yet implemented")
    }
}