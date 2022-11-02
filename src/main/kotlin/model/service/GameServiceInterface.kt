package model.service

import model.Entity.Game
import model.Entity.Message

interface GameServiceInterface {
    fun createGame(game: Game, message: List<Message>): Boolean
    fun readUserGames(userUid: String, message: List<Message>): List<Game>
    fun updateGames(gameUid: String, message: List<Message>): Boolean
}