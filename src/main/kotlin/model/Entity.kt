package model.Entity

import org.springframework.beans.factory.annotation.Value
import java.time.LocalDate
import java.util.*

enum class GameStatus {
    IN_GAME, FINISHED, ERROR, NOT_IN_GAME, NOT_DETERMINED
}

enum class UserRole {
    USER, MODERATOR, ADMIN, NOT_DETERMINED
}
enum class LetterStatus{
    GUESSED, MISSING, IS_ELSEWHERE
}

enum class UserStatus {
    ACTIVE, NOT_ACTIVE, DELETED, NOT_DETERMINED
}

interface Entity {
    val uid: String
    fun getTable(): String
    fun getColumns(): String
    fun getValues(): String
}

//TODO возвращать названия таблиц из пропсов
class User(
    override val uid: String = UUID.randomUUID().toString(),
    val created: LocalDate = LocalDate.now(),
    val role: UserRole = UserRole.USER,
    val status: UserStatus = UserStatus.NOT_DETERMINED,
    val login: String,
    var pass: String
) : Entity {
    override fun getTable(): String {
        return "users"
    }

    override fun getColumns(): String {
        return "(uid, created, role, status, login, pass)"
    }

    override fun getValues(): String {
        return "('$uid', '$created', '${role.name}', '${status.name}', '$login', '$pass')"
    }
}

data class Message(
    val textMessage: String,
    val description: String
)

data class Game(
    override val uid: String = UUID.randomUUID().toString(),
    val created: LocalDate,
    val userUid: String,
    var updated: LocalDate = LocalDate.now(),
    var status: GameStatus,
    var time: String = "",
    var hiddenWord: String = "",
    var countLettersInHiddenWord: Int,
    var countAttempts: Int = 0
) : Entity {
    override fun getTable(): String {
        return "games"
    }


    override fun getColumns(): String {
        return "(uid, created, useruid, updated, status, time, hiddenWord, countAttempts)"
    }

    override fun getValues(): String {
        return "('$uid', '$created', '$userUid', '$updated', '${status.name}', '$time', '$hiddenWord', '$countAttempts')"
    }
}

data class Word(
    override val uid: String = UUID.randomUUID().toString(),
    val wordValue: String,
    val countLetters: String,
) : Entity {
    override fun getTable(): String {
        return "words"
    }

    override fun getColumns(): String {
        return "value, countletters"
    }

    override fun getValues(): String {
        return "($wordValue, $countLetters)"
    }
}