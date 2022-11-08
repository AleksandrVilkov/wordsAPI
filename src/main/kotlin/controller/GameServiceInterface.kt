package controller

import model.Entity.Game
import model.enumCollectilos.LetterStatus
import model.Entity.Message

interface GameServiceInterface {
    fun createGame(game: Game, msgs: MutableList<Message>): Game?
    fun foundUserGameInGame(userUid: String, gameUid: String, msgs: MutableList<Message>): Game?
    fun attemptResult(
        userUid: String,
        gameUid: String,
        attemptWord: String,
        msgs: MutableList<Message>,
        result: MutableMap<String, LetterStatus>
    )

    fun readUserGames(userUid: String, msgs: MutableList<Message>): List<Game>
    fun updateGames(game: Game, msgs: MutableList<Message>): Boolean
}