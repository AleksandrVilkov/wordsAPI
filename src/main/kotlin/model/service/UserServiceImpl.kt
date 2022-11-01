package model.service

import logger.Logger
import logger.LoggerInterface
import model.Entity.User
import model.defineUserRole
import model.defineUserStatus
import model.encode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*


@Component
class UserServiceImpl(
    @Autowired
    private val dbConnector: DataBaseConnector,
    @Autowired
    private val logger: LoggerInterface
) : UserServiceInterface {
    init {
        logger.setNameClass("UserServiceImpl")
    }

    override fun registerUser(user: User): Boolean {
        return if (!checkUser(user.login)) {
            user.pass = encode(user.pass)
            dbConnector.save(user)
            true
        } else {
            logger.debug("Пользователь не создан, так как уже существует")
            false
        }
    }

    override fun findUser(login: String): User? {
        val result = dbConnector.read(
            table = dbConnector.getProperties().getProperty("usersTable"),
            keyParams = "login",
            valueParams = login
        )
        val user = mutableListOf<User>()
        while (result.next()) {
            user.add(
                User
                    (
                    uid = result.getString("uid"),
                    created = Date(result.getString("created")),
                    role = defineUserRole(result.getString("role")),
                    status = defineUserStatus(result.getString("status")),
                    login = result.getString("login"),
                    pass = result.getString("pass")
                )
            )
        }
        if (user.size != 1) {
            logger.debug("User not found or too many found")
            return null
        }
        return user[0]
    }

    override fun deleteUser(login: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun getAllUsers(): List<User> {
        val result = dbConnector.read(
            table = dbConnector.getProperties().getProperty("usersTable"),
            keyParams = "login",
            valueParams = "*"
        )
        val users = mutableListOf<User>()
        while (result.next()) {
            users.add(
                User
                    (
                    uid = result.getString("uid"),
                    created = Date(result.getString("created")),
                    role = defineUserRole(result.getString("role")),
                    status = defineUserStatus(result.getString("status")),
                    login = result.getString("login"),
                    pass = result.getString("pass")
                )
            )
        }
        return users
    }

    private fun checkUser(userLogin: String): Boolean {
        return findUser(userLogin) != null
    }
}