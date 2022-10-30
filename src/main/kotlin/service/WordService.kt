package model

import model.Entity.Word
import service.DataBaseProxyConnector

fun createWordsInDataBase(words: List<Word>, dbConnector: DataBaseProxyConnector) {
    val tableName = dbConnector.getProperties().getProperty("wordsTable")
    var query = "INSERT INTO $tableName (value, countletters, uid) VALUES "
    var iter = 1
    for (word in words) {
        query += "('${word.value}', '${word.countLetters}', '${word.uid}')"
        if (iter != words.size) {
            query += ", "
        } else {
            query += ";"
        }
        iter++
    }
    dbConnector.sendQueryWithoutResult(query)
}

fun readWordsInDataBase(wordValue: String, dbConnector: DataBaseProxyConnector): List<Word> {
    val tableName = dbConnector.getProperties().getProperty("wordsTable")
    val response = dbConnector.sendQuery("SELECT * FROM $tableName WHERE value ='$wordValue'")
    val result = mutableListOf<Word>()
    while (response.next()) {
        result.add(
            Word(
                value = response.getString("value"),
                countLetters = response.getString("countletters"),
                uid = response.getString("uid")
            )
        )
    }
    return result
}

fun deleteWordInDataBase(word: Word, dbConnector: DataBaseProxyConnector) {
    //TODO
}



