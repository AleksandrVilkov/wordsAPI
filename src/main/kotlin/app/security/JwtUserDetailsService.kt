package app.security

import app.controller.UserServiceInterface
import app.controller.getDescription
import app.dto.MessageDto
import app.dto.toEntity
import app.logger.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import app.security.jwt.jwtUserFactory
import org.springframework.stereotype.Component

@Component
class JwtUserDetailsService(
    @Autowired val userService: UserServiceInterface
) : UserDetailsService {
    val logger = Logger("JwtUserDetailsService")
    override fun loadUserByUsername(username: String): UserDetails {
        val msgs = mutableListOf<MessageDto>()
        val user = userService.findUser(username, msgs)
            ?: throw UsernameNotFoundException("User with username $username not found")
        if (msgs.isNotEmpty()) {
            throw UsernameNotFoundException(getDescription(msgs))
        }
        val jwtUser = jwtUserFactory(user.toEntity())
        logger.debug("user with login ${jwtUser.login} successfully loaded")
        return jwtUser


    }
}