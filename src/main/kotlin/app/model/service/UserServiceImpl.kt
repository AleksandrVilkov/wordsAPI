package app.model.service

import app.controller.UserServiceInterface
import app.logger.Logger
import model.Entity.Message
import model.Entity.User
import model.defineUserRole
import model.defineUserStatus
import model.encode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.time.LocalDate


@Service
class UserServiceImpl(
    @Autowired
    private val dbConnector: DataBaseConnector,
) : UserServiceInterface {

    val logger = Logger("UserServiceImpl")

    override fun registerUser(user: User, msgs: MutableList<Message>): Boolean {
        return if (!checkUser(user.login, msgs)) {
            user.pass = encode(user.pass)
            dbConnector.save(user)
            true
        } else {
            val msgTest = "Пользователь не создан, так как уже существует"
            msgs.add(Message(msgTest, ""))
            logger.debug(msgTest)
            false
        }
    }

    override fun findUser(login: String, msgs: MutableList<Message>): User? {
        val result = dbConnector.read(
            table = dbConnector.getProperties().getProperty("usersTable"),
            keyParams = "login",
            valueParams = "'$login'"
        )
        val user = mutableListOf<User>()
        while (result.next()) {
            user.add(
                User
                    (
                    uid = result.getString("uid"),
                    created = LocalDate.parse(result.getString("created")),
                    role = defineUserRole(result.getString("role")),
                    status = defineUserStatus(result.getString("status")),
                    login = result.getString("login"),
                    pass = result.getString("pass")
                )
            )
        }
        if (user.size != 1) {
            val msgText = "User not found or too many found"
            msgs.add(Message(msgText, "count found users: ${user.size}"))
            logger.debug(msgText)
            return null
        }
        return user[0]
    }

    override fun deleteUser(login: String, msgs: MutableList<Message>): Boolean {
        val user = findUser(login, msgs)
        if (user == null) {
            msgs.add(Message("User with login $login not found", ""))
            return false
        }
        return dbConnector.delete(user)
    }

    override fun getAllUsers(msgs: MutableList<Message>): List<User> {
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
                    created = LocalDate.parse(result.getString("created")),
                    role = defineUserRole(result.getString("role")),
                    status = defineUserStatus(result.getString("status")),
                    login = result.getString("login"),
                    pass = result.getString("pass")
                )
            )
        }
        return users
    }

    private fun checkUser(userLogin: String, msgs: MutableList<Message>): Boolean {
        return findUser(userLogin, msgs) != null
    }
}