package model.Entity

import java.util.*

enum class GameStatus {
    IN_GAME, FINISHED, ERROR, NOT_IN_GAME
}


class User(
    val uid: String = UUID.randomUUID().toString(),
    val login: String,
    var pass: String,
    var guessWord: String? = null,
    val uidGames: List<String> = mutableListOf()
)

data class Game(
    val userUid: String,
    val status: GameStatus,
    val time: String,
    val hiddenWord: String,
    val countAttempts: Int
)

data class Word(
    val value: String,
    val countLetters: String,
    val uid: String = UUID.randomUUID().toString()
)