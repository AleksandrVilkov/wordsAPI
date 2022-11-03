package model

import org.junit.jupiter.api.Test

class UtilsTest {
    @Test
    fun decodeTest() {
        val test = "test"
        val encode = encode(test)
        println("encode \"$test\" ---->  $encode")
    }
}