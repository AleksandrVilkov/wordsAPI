package app.dto

import app.entity.WordEntity

data class WordDto(val value: String)

fun WordDto.toEntity(): WordEntity = WordEntity(
    wordValue = value,
    countLetters = value.length
)