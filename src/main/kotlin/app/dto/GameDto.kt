package app.dto

data class GameDto(
    val uid: String,
    val created: String,
    val userUid: String,
    val status: String,
    val hiddenWord: String
)