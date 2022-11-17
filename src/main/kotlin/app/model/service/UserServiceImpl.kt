package app.model.service

import app.controller.UserServiceInterface
import app.dto.MessageDto
import app.dto.UserDto
import app.dto.toEntity
import app.entity.toDto
import app.logger.Logger
import app.repository.UserRepository
import model.encode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    @Autowired
    private val userRepository: UserRepository,
) : UserServiceInterface {
    val logger = Logger("UserServiceImpl")

    override fun registerUser(user: UserDto, msgs: MutableList<MessageDto>): Boolean {
        return if (!checkUser(user.login, msgs)) {
            user.pass = encode(user.pass)
            userRepository.save(user.toEntity())
            true
        } else {
            val msgTest = "Пользователь не создан, так как уже существует"
            msgs.add(MessageDto(msgTest))
            logger.debug(msgTest)
            false
        }
    }

    override fun findUser(login: String, msgs: MutableList<MessageDto>): UserDto? {
        val result = userRepository.findByLogin(login)
        val user = result.map {
            it.toDto()
        }
        if (user.size != 1) {
            val msgText = "User not found or too many found"
            msgs.add(MessageDto("$msgText. count found users: ${user.size}"))
            logger.debug(msgText)
            return null
        }
        return user[0]
    }

    override fun deleteUser(login: String, msgs: MutableList<MessageDto>): Boolean {
        val user = findUser(login, msgs)
        if (user == null) {
            msgs.add(MessageDto("User with login $login not found"))
            return false
        }
        userRepository.delete(user.toEntity())
        return true
    }

    override fun getAllUsers(msgs: MutableList<MessageDto>): List<UserDto> {
        return userRepository.findAll().map {
            it.toDto()
        }
    }

    private fun checkUser(userLogin: String, msgs: MutableList<MessageDto>): Boolean {
        return findUser(userLogin, msgs) != null
    }
}