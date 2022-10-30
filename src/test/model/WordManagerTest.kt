package model

import service.DataBaseProxyConnector
import org.junit.jupiter.api.Test


class WordManagerTest {
    @Test
    fun createWordsInDataBaseTest() {
        val db = DataBaseProxyConnector()
        val result = readWordsInDataBase("время", db)
        println(result)
    }
}
