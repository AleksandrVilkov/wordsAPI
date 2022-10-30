package model

import model.Entity.Game
import model.dataBase.DataBaseProxyConnector

fun createGameInDataBase(game: Game, dbConnector: DataBaseProxyConnector) {
    val tableName = dbConnector.getProperties().getProperty("gamesTable")
    dbConnector.sendQuery("")
}
fun readGameInDataBase(game: Game, dbConnector: DataBaseProxyConnector) {

}

fun updateGameInDataBase(game: Game, dbConnector: DataBaseProxyConnector) {

}

fun deleteGameInDataBase(game: Game, dbConnector: DataBaseProxyConnector) {

}