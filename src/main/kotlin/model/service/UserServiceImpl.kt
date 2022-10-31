package model.service

import model.Entity.User
import model.dataBase.DataBaseProxyConnector
import model.encode
import model.logger.Logger

private val logger = Logger("UserServiceImpl")
private val dbConnector = DataBaseProxyConnector()

class UserServiceImpl : UserServiceInterface {
    override fun registerUser(user: User): Boolean {
        val tableName = dbConnector.getProperties().getProperty("usersTable")
        return if (!checkUser(user.login)) {
            dbConnector.sendQueryWithoutResult(
                "INSERT INTO $tableName " + "VALUES ('${user.uid}', '${user.login}', '${encode(user.pass)}');"
            )
            true
        } else {
            logger.debug("Пользователь не создан, так как уже сужествует")
            false
        }
    }

    override fun findUser(login: String): User? {
        val tableName = dbConnector.getProperties().getProperty("usersTable")
        val response = dbConnector.sendQuery("SELECT * FROM $tableName WHERE login = '$login'")
        val result = mutableListOf<User>()
        while (response.next()) {
            result.add(
                User(
                    login = response.getString("login"),
                    pass = response.getString("pass"),
                    uid = response.getString("uid")
                )
            )
        }
        return if (result.size == 1) {
            result[0]
        } else {
            null
        }
    }

    override fun deleteUser(login: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun getAllUsers(): List<User> {
        TODO("Not yet implemented")
    }

    private fun checkUser(userLogin: String): Boolean {
        return findUser(userLogin) != null
    }
}