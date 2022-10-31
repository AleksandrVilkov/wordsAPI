package model.service

import model.Entity.Word

interface WordServiceInterface {
    fun createWordsInDataBase(words: List<Word>): Boolean
    fun findRandomWord(light: Int): Word
    fun findWord(value: String): Word
}