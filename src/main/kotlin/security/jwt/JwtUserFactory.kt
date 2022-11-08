package security.jwt

import model.Entity.User
import model.enumCollectilos.UserRole
import model.enumCollectilos.UserStatus
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority


fun jwtUserFactory(user: User): JWTUser {
    return JWTUser(
        uid = user.uid,
        created = user.created,
        enabled = user.status.equals(UserStatus.ACTIVE),
        login = user.login,
        pass = user.pass,
        userAuthorities = mapToGrantedAuthorities(mutableListOf(user.role)),
        role = mutableListOf(SimpleGrantedAuthority(user.role.name))
    )
}

/*
    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());

*/

fun mapToGrantedAuthorities(userRoles: MutableCollection<UserRole>): MutableCollection<GrantedAuthority> {
    var result = mutableListOf<GrantedAuthority>()
    for (userRole in userRoles) {
        result.add(SimpleGrantedAuthority(userRole.name))
    }
    return result
}



