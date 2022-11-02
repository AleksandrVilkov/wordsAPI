package security

import logger.Logger
import model.service.UserServiceInterface
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
        val user = userService.findUser(username)
            ?: throw UsernameNotFoundException("User with username $username not found")
        val jwtUser = jwtUserFactory(user)
        logger.debug("user with login $username successfully loaded")
    }
}