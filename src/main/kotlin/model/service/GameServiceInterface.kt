package model.service

import model.Entity.Game

interface GameServiceInterface {
    fun createGame(game: Game): Boolean
    fun readUserGames(userUid: String): List<Game>
    fun updateGames(gameUid: String): Boolean
}