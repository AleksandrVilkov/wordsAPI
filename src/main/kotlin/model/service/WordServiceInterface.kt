package model.service

import model.Entity.Word

interface WordServiceInterface {
    fun createWordsInDataBase(word: Word)
    fun findRandomWord(light: Int): Word
    fun findWord(value: String): Word
}