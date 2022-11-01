package model.service

import model.Entity.Game
import org.springframework.stereotype.Component

@Component
class GameServiceImpl:GameServiceInterface {
    override fun createGame(game: Game): Boolean {
        TODO("Not yet implemented")
    }

    override fun readUserGames(userUid: String): List<Game> {
        TODO("Not yet implemented")
    }

    override fun updateGames(gameUid: String): Boolean {
        TODO("Not yet implemented")
    }
}