package security.jwt

import org.springframework.security.core.AuthenticationException

class JwtAuthException(msg: String?) : AuthenticationException(msg) {
}