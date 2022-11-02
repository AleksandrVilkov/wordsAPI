package controller

import model.Entity.Game
import model.Entity.Message

interface GameServiceInterface {
    fun createGame(game: Game, msgs: MutableList<Message>): Boolean
    fun readUserGames(userUid: String, msgs: MutableList<Message>): List<Game>
    fun updateGames(gameUid: String, msgs: MutableList<Message>): Boolean
}