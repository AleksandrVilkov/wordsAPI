package security.jwt

import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class JwtTokenFilter(
    val jwtTokenProvider: JwtTokenProvider,
) : GenericFilterBean() {
    override fun doFilter(servletRequest: ServletRequest?, servletResponse: ServletResponse?, filterChain: FilterChain?) {
        val token = jwtTokenProvider.resolveToken(servletRequest as HttpServletRequest)
        //TODO
    }
}