package model.service

import logger.Logger
import model.Entity.Game
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

    override fun createGame(game: Game, message: List<Message>): Boolean {
        val word = wordService.findRandomWord(5)
        game.hiddenWord = word?.wordValue.toString()
        if (game.hiddenWord.isEmpty()) {

        }
        return dbConnector.save(game)
        TODO("Not yet implemented")
    }

    override fun readUserGames(userUid: String, message: List<Message>): List<Game> {
        TODO("Not yet implemented")
    }

    override fun updateGames(gameUid: String, message: List<Message>): Boolean {
        TODO("Not yet implemented")
    }
}