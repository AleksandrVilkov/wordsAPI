package app.security

import app.controller.UserServiceInterface
import app.controller.getDescription
import app.logger.Logger
import model.Entity.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import app.security.jwt.jwtUserFactory
import org.springframework.stereotype.Component

@Component
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
        logger.debug("user with login ${jwtUser.login} successfully loaded")
        return jwtUser


    }
}