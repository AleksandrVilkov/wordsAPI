package app.controller

import model.Entity.Message
import model.Entity.User

interface UserServiceInterface {
    fun registerUser(user: User, msgs: MutableList<Message>): Boolean
    fun findUser(login: String, msgs: MutableList<Message>): User?
    fun deleteUser(login: String, msgs: MutableList<Message>): Boolean
    fun getAllUsers(msgs: MutableList<Message>): List<User>
}
