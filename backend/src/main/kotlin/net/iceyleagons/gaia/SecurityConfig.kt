package net.iceyleagons.gaia

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .httpBasic().disable()
            .csrf().disable() // CSRF must be disabled due to how the frontend is set up currently. We could fix it, but we rather spend the time on more important things. It's not going to production at the moment anyway.
            .authorizeRequests().anyRequest().permitAll()
        return http.build()
    }
}