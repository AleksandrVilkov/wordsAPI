package com.vilkov.words

import model.Entity.Word
import model.createWordsInDataBase
import service.DataBaseProxyConnector
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

fun main(args: Array<String>) {
    createTablesInDB()
}

private fun createTablesInDB() {
    val dbConnector = DataBaseProxyConnector()
    dropTableWithWords(dbConnector)
    createWordsTable(dbConnector)
    fillDatabaseWords(dbConnector)
    createGamesTable(dbConnector)
    createUsersTable(dbConnector)
}

private fun fillDatabaseWords(connector: DataBaseProxyConnector) {

    val words = mutableListOf<Word>()
    val wordsList = getWordsListFromFile("src/main/resources/text/allWords.txt")
    for (wordValue in wordsList) {
        words.add(Word(value = wordValue, countLetters = wordValue.length.toString()))
    }
    createWordsInDataBase(words, connector)
}

private fun getWordsListFromFile(path: String): List<String> {
    val result = mutableListOf<String>()
    try {
        BufferedReader(FileReader(File(path)))
            .use { br ->
                br.lines().forEach { result.add(it) }
            }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return result
}

private fun dropTableWithWords(connector: DataBaseProxyConnector) {
    val query = "DROP TABLE ${connector.getProperties().getProperty("wordsTable")};"
    connector.sendQueryWithoutResult(query)
}

private fun createWordsTable(connector: DataBaseProxyConnector) {
    val wordsTable =
        "CREATE TABLE IF NOT EXISTS ${connector.getProperties().getProperty("wordsTable")}" +
                "(value varchar, countLetters varchar, uid varchar);"
    connector.sendQueryWithoutResult(wordsTable)
}

private fun createUsersTable(connector: DataBaseProxyConnector) {
    val usersQuery =
        "CREATE TABLE IF NOT EXISTS ${connector.getProperties().getProperty("usersTable")}" +
                "(uid varchar, login varchar, pass varchar, guessWord varchar, uidGames varchar);"
    connector.sendQueryWithoutResult(usersQuery)
}

private fun createGamesTable(connector: DataBaseProxyConnector) {
    val gamesQuery =
        "CREATE TABLE IF NOT EXISTS ${connector.getProperties().getProperty("gamesTable")}" +
                "(userUid varchar, status varchar, time varchar, hiddenWord varchar, countAttempts varchar);"
    connector.sendQueryWithoutResult(gamesQuery)
}