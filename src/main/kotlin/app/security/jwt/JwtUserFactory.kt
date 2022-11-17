package app.security.jwt

import app.entity.UserEntity
import app.model.enumCollectilos.UserRole
import app.model.enumCollectilos.UserStatus
import model.defineUserRole
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority


fun jwtUserFactory(user: UserEntity): JWTUser {
    return JWTUser(
        id = user.id,
        created = user.created,
        enabled = user.status.equals(UserStatus.ACTIVE),
        login = user.login,
        pass = user.pass,
        userAuthorities = mapToGrantedAuthorities(mutableListOf(defineUserRole(user.role))),
        role = mutableListOf(SimpleGrantedAuthority(user.role))
    )
}

fun mapToGrantedAuthorities(userRoles: MutableCollection<UserRole>): MutableCollection<GrantedAuthority> {
    val result = mutableListOf<GrantedAuthority>()
    for (userRole in userRoles) {
        result.add(SimpleGrantedAuthority(userRole.name))
    }
    return result
}



