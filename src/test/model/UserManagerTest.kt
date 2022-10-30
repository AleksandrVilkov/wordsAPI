package model

import model.Entity.User
import model.dataBase.DataBaseProxyConnector
import org.junit.jupiter.api.Test

class UserManagerTest {
    @Test
    fun createUserInDataBaseTest() {
        createUserInDataBase(User(login = "testTest", pass = "test"), DataBaseProxyConnector())
    }

    @Test
    fun readUserInDataBaseTest() {
        val user = readUserInDataBase("test", DataBaseProxyConnector())
        println(user)
    }

    @Test
    fun changeUserPassTest() {
        val user = User(login = "testTest", pass = "test")
        changeUserPass(user, "test4", DataBaseProxyConnector())
    }
}