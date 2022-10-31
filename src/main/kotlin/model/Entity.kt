package model.Entity

import java.util.*

enum class GameStatus {
    IN_GAME, FINISHED, ERROR, NOT_IN_GAME
}

enum class UserRole {
    ROLE_USER, ROLE_MODERATOR, ROLE_ADMIN
}

enum class UserStatus {
    ACTIVE, NOT_ACTIVE, DELETED, NOT_DETERMINED
}


class User(
    val uid: String = UUID.randomUUID().toString(),
    val created: Date = Date(),
    val role: List<UserRole> = listOf(),
    val status: UserStatus = UserStatus.NOT_DETERMINED,
    val login: String,
    var pass: String
)

data class Game(
    val uid: String = UUID.randomUUID().toString(),
    val created: Date = Date(),
    val userUid: String,
    val updated: Date,
  //  val status: GameStatus,
    val time: String,
    val hiddenWord: String,
    val countAttempts: Int
)

data class Word(
    val value: String,
    val countLetters: String,
)