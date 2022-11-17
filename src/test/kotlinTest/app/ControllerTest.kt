package app

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class Test {
    @Autowired
    private var mvc: MockMvc? = null

    @Value("\${user.check}")
    private val userCheckUrl: String? = null

    @Value("\${user.save}")
    private val userSaveUrl: String? = null

    @Value("\${game.start}")
    private val gameStartUrl: String? = null

    @Value("\${game.check}")
    private val gameCheckUrl: String? = null

    @Value("\${game.history}")
    private val gameHistoryUrl: String? = null

    @Value("\${game.save.win}")
    private val gameSaveWinUrl: String? = null

    @Value("\${game.attempt}")
    private val gameAttemptUrl: String? = null

    @Value("\${auth.login}")
    private val authLoginUrl: String? = null

    init {
        println("${Date()} Start test controllers...")
    }

    @Test
    fun testStartGameEndpoint() {
        val result = mvc?.perform(
            MockMvcRequestBuilders.get(gameStartUrl.toString())
                .param("userId", "1")
                .param("countLettersInWord","5")
        )?.andExpect(status().is4xxClientError)?.andReturn()
        println("-->> sending request to ${userCheckUrl}: \n" +
                "${result?.request?.parameterMap.toString()} \n" +
                "-->> getting response from $userCheckUrl: \n" +
                "${result?.response?.contentAsString}\n")
    }

    @Test
    fun testTryCheckEndpoint() {

    }

    @Test
    fun testGetAllHistoryEndpoint() {

    }

    @Test
    fun testSaveDefeatEndpoint() {

    }

    @Test
    fun testMakeAttemptEndpoint() {

    }

    @Test
    fun testCheckUserEndpoint() {
        val result = mvc?.perform(
            MockMvcRequestBuilders.get(userCheckUrl.toString())
                .param("login", "test")
        )?.andExpect(status().isOk())?.andReturn()
        println("-->> sending request to ${userCheckUrl}: \n" +
                "${result?.request?.parameterMap.toString()} \n" +
                "-->> getting response from $userCheckUrl: \n" +
                "${result?.response?.contentAsString}\n")
        result?.response?.contentAsString?.isNotEmpty()?.let { Assert.assertTrue(it) }
    }

    @Test
    fun testSaveUserEndpoint() {

    }
}