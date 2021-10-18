package com.evolutivecode.opccadminportal.common.config

import com.evolutivecode.opccadminportal.application.handler.CustomAuthenticationFailureHandler
import com.evolutivecode.opccadminportal.application.handler.CustomAuthenticationProvider
import com.evolutivecode.opccadminportal.application.handler.CustomAuthenticationSuccessHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import kotlin.jvm.Throws

@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private val customAuthenticationProvider : CustomAuthenticationProvider? = null
    @Autowired
    private val successHandler: CustomAuthenticationSuccessHandler? = null
    @Autowired
    private val failureHandler: CustomAuthenticationFailureHandler? = null

    private val logger : Logger = LoggerFactory.getLogger(SecurityConfig::class.java)

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(customAuthenticationProvider)
    }

    @Autowired
    fun configureAuto(auth: AuthenticationManagerBuilder){
        auth
                .inMemoryAuthentication()
                .withUser("user").password(passwordEncoder()?.encode("password")).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder()?.encode("admin")).roles("ADMIN")
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable().authorizeRequests()
            .antMatchers(
                "/login",
                "/dashboard",
                "/portal-users",
                "/forgot-password",
                "/reset-password")
            .permitAll()
            .anyRequest().authenticated()
            .and().formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .successHandler(successHandler)
            .failureHandler(failureHandler)
            .usernameParameter("username")
            .passwordParameter("password")
            .permitAll().and()
            .logout().logoutUrl("/logout")

    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring()
            .antMatchers(
                "/ajax/**",
                "/css/**",
                "/img/**",
                "/js/**",
                "/master/**",
                "/vendor/**"
            )
    }
}
