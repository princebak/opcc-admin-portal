package com.evolutivecode.opccadminportal.infra.externalservice

import com.evolutivecode.opccadminportal.core.domain.user.User
import com.evolutivecode.opccadminportal.core.domain.user.UserAuthenticate
import com.evolutivecode.opccadminportal.core.domain.user.UserInfo
import com.evolutivecode.opccadminportal.infra.dto.auth.ResetPasswordRequestDto
import com.evolutivecode.opccadminportal.infra.dto.auth.UpdatePasswordDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.retry.RetryCallback
import org.springframework.retry.support.RetryTemplate
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.lang.Exception
import java.net.URI

@Component
class AuthenticationExternalService (
    private val restTemplate: RestTemplate,
    private val retryTemplate: RetryTemplate,
    @Value("\${service.authentication.base.url}") private val authenticationUrl: String,
    @Value("\${service.authentication.info.url}") private val userInfoUrl: String,
    @Value("\${service.authentication.reset.url}") private val resetPasswordUrl: String,
    @Value("\${service.authentication.update.password.url}") private val updatePasswordUrl: String
) {

    val logger: Logger = LoggerFactory.getLogger(AuthenticationExternalService::class.java)

    fun create(user: User) {
        logger.info("out create user account ${user.email}")
        restTemplate.postForObject(authenticationUrl,user, Void::class.java)
    }

    fun authenticate(authentication: Authentication?): String?{
        logger.info("out authenticate user")
        val userAuth = UserAuthenticate(authentication!!.name, authentication.credentials.toString())
        return retryTemplate.execute(RetryCallback<String, Exception>{
            restTemplate.postForObject(authenticationUrl.plus("/authenticate"),userAuth,String::class.java)
        })
    }

    fun getUserInfo(token: String): UserInfo?{
        val url: URI? = URI(String.format(userInfoUrl,token))
        logger.info("out get user infos")
        return retryTemplate.execute(RetryCallback<UserInfo, Exception>{restTemplate.getForObject(url!!, UserInfo::class.java)})
    }

    fun resetPasswordRequest(requestDto: ResetPasswordRequestDto) :String {
        return restTemplate.postForObject(resetPasswordUrl, requestDto, String::class.java)!!
    }

    fun updatePassword(updatePasswordDto: UpdatePasswordDto) : String {
        return restTemplate.postForObject(updatePasswordUrl, updatePasswordDto, String::class.java)!!
    }
}
