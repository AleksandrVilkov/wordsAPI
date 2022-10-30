package model

import model.dataBase.PSQLConnector
import org.junit.jupiter.api.Test

class PSQLConnectorTest {
    @Test
    fun getConnectorTest() {
        val connector = PSQLConnector()
        val list = listOf<String>("rr", "ee")
        val user = connector.sendQuery("rrrrr")
        println(user)
    }
}