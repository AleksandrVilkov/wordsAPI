package security.jwt

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import model.Entity.UserRole
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest


@Component
class JwtTokenProvider(
    @Value("\${jwt.token.secret}")
    var secret: String,
    @Value("\${jwt.token.expired}")
    val validityInMillisecond: Long,
    @Autowired
    val userDetailsService: UserDetailsService

) {
    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @PostConstruct
    fun init() {
        val byteSecret = (secret.toByteArray())
        println(byteSecret.size)
        secret = Base64.getEncoder().encodeToString(byteSecret)
    }

    fun createToken(userName: String, roles: MutableCollection<UserRole>): String {
        val claims = Jwts.claims().setSubject(userName)
        claims["roles"] = getRoleNames(roles)
        val date = Date()
        val expiration = date.time + validityInMillisecond
        return Jwts.builder()
            .setSubject(claims.toString())
            .setIssuedAt(date)
            .setExpiration(Date(expiration))
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact()
    }

    fun getAuthentication(token: String): Authentication {
        val userDatails = userDetailsService.loadUserByUsername(getUserName(token))
        return UsernamePasswordAuthenticationToken(userDatails, "", userDatails.authorities)
    }
    fun resolveToken(req: HttpServletRequest): String? {
        val bearerToken = req.getHeader("Authorization")
        if(bearerToken != null && bearerToken.startsWith("Bearer_"))
            return bearerToken.substring(7, bearerToken.length)

        return null
    }

    fun getUserName(token: String): String {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body.subject;
    }

    fun validateToken(token: String): Boolean {
        return try {
            val claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
            !claims.body.expiration.before(Date())
        } catch (e: JwtException) {
            throw JwtAuthException("JWT token is expired or invalid")
        }
    }

    fun getRoleNames(userRoles: MutableCollection<UserRole>): MutableCollection<String> {
        val result = mutableListOf<String>()
        for (userRole in userRoles) {
            result.add(userRole.name)
        }
        return result
    }
}