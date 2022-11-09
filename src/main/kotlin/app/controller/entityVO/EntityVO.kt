package app.controller.entityVO

enum class Status {
    OK, ERROR
}

interface EntityVO {
}

class WordVO(val value: String) : EntityVO

class AuthRequest(
    val login: String,
    val pass: String
)

class UserVO(
    val created: String,
    val role: String,
    val status: String,
    val login: String,
) : EntityVO

class GameVO(
    val uid: String,
    val created: String,
    val userUid: String,
    val status: String,
    val hiddenWord: String
) : EntityVO
