package app

import app.model.enumCollectilos.UserRole
import app.security.jwt.JwtTokenProvider
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class Test {
    @Autowired
    private var mvc: MockMvc? = null
    @Autowired
    private var jwtToken: JwtTokenProvider? = null
@Test
fun test() {
    val login = "test"
    val pass = "test"
    val body = "{\"login\":\"$login\", \"pass\":\"$pass\"}"
    println(body)
    val result = mvc?.perform(MockMvcRequestBuilders.post("/auth/login").content(body))?.andExpect(status().isOk())?.andReturn()
    println(result?.response?.contentAsString)
    }
}