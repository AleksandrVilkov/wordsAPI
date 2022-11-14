package app

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class Test {
    @Autowired
    private var mvc: MockMvc? = null

    private lateinit var token: String

    @Test
    @WithMockUser
    fun test() {
        val result = mvc?.perform(
            MockMvcRequestBuilders.get("/user/check")
                .param("login", "test")
        )?.andExpect(status().isOk())?.andReturn()
        println(result?.response?.contentAsString)
    }

//    @Before
//    fun getTokenForTestUsers() {
//        val login = "test"
//        val pass = "test"
//        val body = "{\"login\":\"$login\", \"pass\":\"$pass\"}"
//        println(body)
//        val result = mvc?.perform(
//            MockMvcRequestBuilders.post("/auth/login").content(body).contentType(MediaType.APPLICATION_JSON)
//        )?.andExpect(status().isOk())?.andReturn()
//        this.token = result?.response?.contentAsString
//            ?.split("token\":\"")?.get(1)?.replace("\"", "")?.replace("}", "").toString()
//    }
}