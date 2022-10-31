package model.service

import model.Entity.Word

interface WordServiceInterface {
    fun createWord(word: Word)
    fun findRandomWord(countLetters: Int): Word
    fun findWord(value: String): Word
}