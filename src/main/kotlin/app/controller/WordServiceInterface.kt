package app.controller

import app.dto.MessageDto
import app.dto.WordDto

interface WordServiceInterface {
    fun createWord(word: WordDto, msgs: MutableList<MessageDto>)
    fun findRandomWord(countLetters: Int, msgs: MutableList<MessageDto>): WordDto?
    fun findWord(value: String, msgs: MutableList<MessageDto>): WordDto?
}