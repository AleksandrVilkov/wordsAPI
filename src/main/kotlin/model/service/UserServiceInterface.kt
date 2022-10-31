package model.service

import model.Entity.User
import model.encode

interface UserServiceInterface {
    fun registerUser(user: User): Boolean
    fun findUser(login: String): User?
    fun deleteUser(login: String): Boolean
    fun getAllUsers(): List<User>
}
