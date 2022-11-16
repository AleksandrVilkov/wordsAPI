package app.controller

import app.dto.MessageDto
import app.dto.UserDto

interface UserServiceInterface {
    fun registerUser(user: UserDto, msgs: MutableList<MessageDto>): Boolean
    fun findUser(login: String, msgs: MutableList<MessageDto>): UserDto?
    fun deleteUser(login: String, msgs: MutableList<MessageDto>): Boolean
    fun getAllUsers(msgs: MutableList<MessageDto>): List<UserDto>
}
