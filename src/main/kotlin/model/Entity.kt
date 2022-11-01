package model.Entity

import java.time.LocalDate
import java.util.*

enum class GameStatus {
    IN_GAME, FINISHED, ERROR, NOT_IN_GAME
}

enum class UserRole {
    ROLE_USER, ROLE_MODERATOR, ROLE_ADMIN, NOT_DETERMINED
}

enum class UserStatus {
    ACTIVE, NOT_ACTIVE, DELETED, NOT_DETERMINED
}

interface Entity {
    fun getTable(): String
    fun getColumn(): String
    fun getValue(): String
}

class User(
    val uid: String = UUID.randomUUID().toString(),
    val created: LocalDate = LocalDate.now(),
    val role: UserRole = UserRole.ROLE_USER,
    val status: UserStatus = UserStatus.NOT_DETERMINED,
    val login: String,
    var pass: String
) : Entity {
    override fun getTable(): String {
        return "users"
    }

    override fun getColumn(): String {
        return "(uid, created, role, status, login, pass)"
    }

    override fun getValue(): String {
        return "($uid, $created, ${role.name}, ${status.name}, $login, $pass)"
    }
}

data class Game(
    val uid: String = UUID.randomUUID().toString(),
    val created: Date = Date(),
    val userUid: String,
    val updated: Date,
    val status: GameStatus,
    val time: String,
    val hiddenWord: String,
    val countAttempts: Int
) : Entity {
    override fun getTable(): String {
        return "games"
    }

    override fun getColumn(): String {
        return "(uid, created, userUID, updated, status, time, hiddenWord, countAttempts)"
    }

    override fun getValue(): String {
        return "($uid, $created, $userUid, $updated, ${status.name}, $time, $hiddenWord, $countAttempts)"
    }
}

data class Word(
    val wordValue: String,
    val countLetters: String,
) : Entity {
    override fun getTable(): String {
        return "words"
    }

    override fun getColumn(): String {
        return "value, countletters"
    }

    override fun getValue(): String {
        return "($wordValue, $countLetters)"
    }
}