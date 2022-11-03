package security.jwt

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate

class JWTUser(
    val uid: String,
    val created: LocalDate,
    val role: MutableCollection<out GrantedAuthority>,
    val enabled: Boolean,
    val login: String,
    val pass: String,
    val userAuthorities: MutableCollection<out GrantedAuthority>
    // val lastPasswordResetDate: LocalDate,
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return userAuthorities
    }

    override fun getPassword(): String {
        return pass
    }

    override fun getUsername(): String {
        return login
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return enabled
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}