package security.jwt

import model.Entity.User
import model.Entity.UserStatus
import org.springframework.security.core.authority.SimpleGrantedAuthority


fun jwtUserFactory(user: User): JWTUser {
    return JWTUser(
        uid = user.uid,
        created = user.created,
        enabled = user.status.equals(UserStatus.ACTIVE),
        login = user.login,
        pass = user.pass,
        role = mutableListOf(SimpleGrantedAuthority(user.role.name))
    )
}



