package app.dto

import app.entity.GameEntity

data class GameDto(
    val id: Int,
    val created: String,
    val userId: String,
    var status: String,
    val countLettersInHiddenWord: Int,
    var hiddenWord: String,
    var countAttempts: Int,
    var updated: String,
    var attemptWords: MutableList<String>
)

fun GameDto.toEntity(): GameEntity = GameEntity(
    id = this.id,
    created = this.created,
    userId = this.userId,
    status = this.status,
    countLettersInHiddenWord = this.countLettersInHiddenWord,
    hiddenWord = this.hiddenWord,
    countAttempts = this.countAttempts
)