package controller

import model.Entity.Message
import model.Entity.Word

interface WordServiceInterface {
    fun createWord(word: Word, msgs: MutableList<Message>)
    fun findRandomWord(countLetters: Int, msgs: MutableList<Message>): Word?
    fun findWord(value: String, msgs: MutableList<Message>): Word?
}