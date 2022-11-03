package security

import controller.UserServiceInterface
import controller.getDescription
import logger.Logger
import model.Entity.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import security.jwt.jwtUserFactory

@Service
class JwtUserDetailsService(
    @Autowired
    val userService: UserServiceInterface
) : UserDetailsService {
    val logger = Logger("JwtUserDetailsService")
    override fun loadUserByUsername(username: String): UserDetails {
        val msgs = mutableListOf<Message>()
        val user = userService.findUser(username, msgs)
            ?: throw UsernameNotFoundException("User with username $username not found")
        if (msgs.isNotEmpty()) {
            throw UsernameNotFoundException(getDescription(msgs))
        }
        val jwtUser = jwtUserFactory(user)

        logger.debug("user with login $username successfully loaded")
        TODO()
    }
}