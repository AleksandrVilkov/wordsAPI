package model.service

import model.Entity.User
import model.logger.Logger


class UserServiceImpl : UserServiceInterface {
    private val logger = Logger("UserServiceImpl")
    private val dbConnector: DataBaseConnector = TODO()
    override fun registerUser(user: User): Boolean {
        return if (!checkUser(user.login)) {
            dbConnector.save(user)
            true
        } else {
            logger.debug("Пользователь не создан, так как уже сужествует")
            false
        }
    }

    override fun findUser(login: String): Any? {
        val result = dbConnector.read(login)
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