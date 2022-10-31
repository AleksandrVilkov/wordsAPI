package model.service

import model.Entity.User
import model.Entity.Word
import model.logger.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class UserServiceImpl(
    @Autowired
    private val dbConnector: DataBaseConnector
) : UserServiceInterface {
    private val logger = Logger("UserServiceImpl")

    override fun registerUser(user: User): Boolean {
        return if (!checkUser(user.login)) {
            dbConnector.save(user)
            true
        } else {
            logger.debug("Пользователь не создан, так как уже сужествует")
            false
        }
    }

    override fun findUser(login: String): User? {
        return null
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