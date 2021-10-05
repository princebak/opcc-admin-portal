package com.evolutivecode.opccadminportal.application.handler

import com.evolutivecode.opccadminportal.common.exception.LoginFailedException
import com.evolutivecode.opccadminportal.common.util.Constant
import com.evolutivecode.opccadminportal.core.service.AuthenticationService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.util.ArrayList

@Component
class CustomAuthenticationProvider(private val authenticationService : AuthenticationService) : AuthenticationProvider {
     override fun authenticate(authentication: Authentication?): Authentication {
         val token = authenticationService.authenticate(authentication)

         if(token != null){
             val grantedAuths = ArrayList<GrantedAuthority>()
             grantedAuths.add(SimpleGrantedAuthority("ROLE_USER"))
             val principal: User? = User(token, token, grantedAuths)
             return UsernamePasswordAuthenticationToken(principal, token, grantedAuths)
         }else{
             throw LoginFailedException(Constant.LOGIN_FAILED)
         }
    }

     override fun supports(authentication: Class<*>?): Boolean {
        return true
    }

}
