package net.iceyleagons.junction

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.httpBasic().disable().csrf().disable().cors().disable().authorizeRequests().anyRequest().authenticated()
        return http.build()
    }
}