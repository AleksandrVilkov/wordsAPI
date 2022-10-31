package model.service

import model.Entity.Word
import model.dataBase.DataBaseProxyConnector

private val dbConnector = DataBaseProxyConnector()

class WordServiceImpl : WordServiceInterface {
    override fun createWordsInDataBase(words: List<Word>): Boolean {
        val tableName = dbConnector.getProperties().getProperty("wordsTable")
        var query = "INSERT INTO $tableName (value, countletters, uid) VALUES "
        var iter = 1
        for (word in words) {
            query += "('${word}', '${word.countLetters}')"
            if (iter != words.size) {
                query += ", "
            } else {
                query += ";"
            }
            iter++
        }
        dbConnector.sendQueryWithoutResult(query)
        return true
    }

    override fun findRandomWord(light: Int): Word {
        TODO("Not yet implemented")
    }

    override fun findWord(value: String): Word {
        TODO("Not yet implemented")
    }
}