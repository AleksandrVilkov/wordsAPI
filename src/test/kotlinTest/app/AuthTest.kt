package app

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthTest {
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
        val dateNow = Date()
        println("$dateNow Start test authorization request checks")
    }

    @Test
    fun checkAuthUserCheck() {
        mvc?.perform(
            MockMvcRequestBuilders.get(userCheckUrl.toString()).param("login", "test")
        )?.andExpect(status().isForbidden)
    }

    @Test
    fun checkAuthUserSave() {
        mvc?.perform(
            MockMvcRequestBuilders.get(userSaveUrl.toString()).param("login", "test").param("pass", "test")
        )?.andExpect(status().isInternalServerError)
    }

    @Test
    fun checkAuthGameStart() {
        mvc?.perform(
            MockMvcRequestBuilders.get(gameStartUrl.toString()).param("userUid", "").param("countLettersInWord", "4")
        )?.andExpect(status().isForbidden)
    }

    @Test
    fun checkAuthGameCheck() {
        mvc?.perform(MockMvcRequestBuilders.get(gameCheckUrl.toString()).param("word", "привет"))
            ?.andExpect(status().isForbidden)
    }

    @Test
    fun checkAuthGameHistory() {
        mvc?.perform(MockMvcRequestBuilders.get(gameHistoryUrl.toString()).param("userUid", ""))
            ?.andExpect(status().isForbidden)
    }

    @Test
    fun checkAuthGameSaveWin() {
        mvc?.perform(
            MockMvcRequestBuilders.get(gameSaveWinUrl.toString()).param("userUid", "").param("gameUid", "")
        )?.andExpect(status().isForbidden)
    }

    @Test
    fun checkAuthGameAttempt() {
        mvc?.perform(
            MockMvcRequestBuilders.get(gameAttemptUrl.toString()).param("userUid", "").param("gameUid", "")
                .param("attemptWord", "")
        )?.andExpect(status().isForbidden)
    }

    @Test
    fun checkAuthAuthLogin() {
        val login = "test"
        val pass = "test"
        val body = "{\"login\":\"$login\", \"pass\":\"$pass\"}"
        mvc?.perform(
            MockMvcRequestBuilders.post(authLoginUrl.toString()).content(body).contentType(MediaType.APPLICATION_JSON)

        )?.andExpect(status().isOk)
    }
}
