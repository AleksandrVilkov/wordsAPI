package app.controller

import app.dto.GameDto
import app.dto.MessageDto
import app.model.enumCollectilos.LetterStatus

interface GameServiceInterface {
    fun createGame(game: GameDto, msgs: MutableList<MessageDto>): GameDto
    fun foundUserGameInGame(userId: Int, gameId: Int, msgs: MutableList<MessageDto>): GameDto?
    fun attemptResult(
        userId: Int,
        gameId: Int,
        attemptWord: String,
        msgs: MutableList<MessageDto>,
        result: MutableMap<String, LetterStatus>
    )

    fun readUserGames(userId: Int, msgs: MutableList<MessageDto>): List<GameDto>
    fun updateGames(game: GameDto, msgs: MutableList<MessageDto>): Boolean
}