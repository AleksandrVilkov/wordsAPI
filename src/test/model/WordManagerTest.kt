package model

import model.dataBase.DataBaseProxyConnector
import org.junit.jupiter.api.Test
import org.springframework.util.Assert


class WordManagerTest {
    @Test
    fun createWordsInDataBaseTest() {
        val db = DataBaseProxyConnector()
        val result = readWordsInDataBase("время", db)
        println(result)
    }
}
