package app.security.jwt

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class JwtTokenFilter(
    @Autowired
    val jwtTokenProvider: JwtTokenProvider

) : GenericFilterBean() {
    override fun doFilter(
        servletRequest: ServletRequest?,
        servletResponse: ServletResponse?,
        filterChain: FilterChain?
    ) {
        val token = jwtTokenProvider.resolveToken(servletRequest as HttpServletRequest)
        if (token != null && jwtTokenProvider.validateToken(token)) {
            val authentication = jwtTokenProvider.getAuthentication(token)
            SecurityContextHolder.getContext().authentication = authentication
        }

        if (!servletRequest.pathInfo.equals("/auth/login"))
        filterChain?.doFilter(servletRequest, servletResponse)
    }
}