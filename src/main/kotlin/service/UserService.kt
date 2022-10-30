package model

import model.Entity.User
import service.DataBaseProxyConnector
import model.logger.Logger

private val logger = Logger("UserManager")

fun createUserInDataBase(user: User, dbConnector: DataBaseProxyConnector): Boolean {
    val tableName = dbConnector.getProperties().getProperty("usersTable")
    return if (!checkUser(user.login, dbConnector)) {
        dbConnector.sendQueryWithoutResult(
            "INSERT INTO $tableName " + "VALUES ('${user.uid}', '${user.login}', '${encode(user.pass)}', '${user.guessWord}', '${user.uidGames}');"
        )
        true
    } else {
        logger.debug("Пользователь не создан, так как уже сужествует")
        false
    }


}

fun readUserInDataBase(userLogin: String, dbConnector: DataBaseProxyConnector): User? {
    val users = readUsersInDataBase(userLogin, dbConnector)
    if (users.size == 1) {
        return users[0]
    }
    logger.warn("Ошибка в получении пользователя")
    return null
}

fun checkUser(userLogin: String, dbConnector: DataBaseProxyConnector): Boolean {
    return readUserInDataBase(userLogin, dbConnector) != null
}

fun changeUserPass(user: User, newPass: String, dbConnector: DataBaseProxyConnector): Boolean {
    val userFromDB = readUserInDataBase(user.login, dbConnector) ?: return false
    if (userFromDB.pass == encode(user.pass)) {
        if (encode(newPass) == userFromDB.pass) {
            logger.debug("The user ${user.login} has a new password that matches the old one")
            return false
        }
        dbConnector.sendQueryWithoutResult(
            "UPDATE ${dbConnector.getProperties().getProperty("usersTable")} " +
                    "SET pass = '${encode(newPass)}';"
        )
        logger.debug("User password changed  for ${user.login}")
        return true
    } else {
        logger.error("Failed to change password. User authorization error")
        return false
    }
}

fun addNewGameToUser(user: User, dbConnector: DataBaseProxyConnector, uidNewGame: String): Boolean {
    //TODO
    return false
}

fun deleteUserInDataBase(user: User, dbConnector: DataBaseProxyConnector) {
    //TODO
}

private fun readUsersInDataBase(userLogin: String, dbConnector: DataBaseProxyConnector): List<User> {
    val tableName = dbConnector.getProperties().getProperty("usersTable")
    val response = dbConnector.sendQuery("SELECT * FROM $tableName WHERE login = '$userLogin'")
    val result = mutableListOf<User>()
    while (response.next()) {
        result.add(
            User(
                login = response.getString("login"),
                pass = response.getString("pass"),
                uid = response.getString("uid"),
                guessWord = response.getString("guessword"),
                uidGames = response.getString("uidgames").split(", ")
            )
        )
    }
    return result
}