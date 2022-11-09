package app.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import app.security.jwt.JwtConfigure
import app.security.jwt.JwtTokenProvider

@Configuration
class SecurityConfig(
    @Autowired
    val jwtTokenProvider: JwtTokenProvider,
    @Value("\${admin.endpoint}")
    val adminEndpoint: String,
    @Value("\${login.endpoint}")
    val loginEndpoint: String
) : WebSecurityConfigurerAdapter() {

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(loginEndpoint).permitAll()
            .antMatchers(adminEndpoint).permitAll()
            .anyRequest().authenticated()
            .and()
            .apply(JwtConfigure(jwtTokenProvider))
    }
}