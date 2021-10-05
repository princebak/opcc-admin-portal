package com.evolutivecode.opccadminportal.core.service.manager

import com.evolutivecode.opccadminportal.application.form.ResetPasswordForm
import com.evolutivecode.opccadminportal.common.exception.InternalServerException
import com.evolutivecode.opccadminportal.common.exception.LoginFailedException
import com.evolutivecode.opccadminportal.common.exception.SignUpFailedException
import com.evolutivecode.opccadminportal.common.exception.UnauthorizedLoginException
import com.evolutivecode.opccadminportal.common.util.Constant
import com.evolutivecode.opccadminportal.core.domain.user.User
import com.evolutivecode.opccadminportal.core.domain.user.UserInfo
import com.evolutivecode.opccadminportal.core.service.AuthenticationService
import com.evolutivecode.opccadminportal.infra.dto.auth.ResetPasswordRequestDto
import com.evolutivecode.opccadminportal.infra.dto.auth.UpdatePasswordDto
import com.evolutivecode.opccadminportal.infra.externalservice.AuthenticationExternalService
import javassist.NotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import java.lang.RuntimeException

@Service
class AuthenticationManager(private val authenticationExternalService : AuthenticationExternalService) : AuthenticationService{

    private val logger: Logger = LoggerFactory.getLogger(AuthenticationManager::class.java)
    companion object {
        const val PROFILE = "opcc"
    }

    override fun getUserInfo(token: String): UserInfo {
        return authenticationExternalService.getUserInfo(token) ?: throw NotFoundException("receiver not found")
    }

    override fun createUser(user: User) {

        try {
            logger.info("auth manager : out authenticate...")
            authenticationExternalService.create(user)
        }
        catch (httpException: HttpClientErrorException) {
            if (httpException.statusCode == HttpStatus.FORBIDDEN ||
                httpException.statusCode == HttpStatus.UNPROCESSABLE_ENTITY ||
                httpException.statusCode == HttpStatus.CONFLICT) {
                throw SignUpFailedException(Constant.SIGN_UP_FAILED)
            }
            throw InternalServerException(Constant.UNEXPECTED_ERROR)
        }
        catch (exception: Exception) {
            logger.warn(exception.message)
            throw InternalServerException(Constant.UNEXPECTED_ERROR)
        }
    }

    override fun authenticate(authentication: Authentication?): String? {
        try {
            return authenticationExternalService.authenticate(authentication)
        }
        catch (httpException: HttpClientErrorException) {
            if (httpException.statusCode == HttpStatus.UNAUTHORIZED) {
                throw LoginFailedException(Constant.LOGIN_FAILED)
            }
            else if (httpException.statusCode == HttpStatus.LOCKED) {
                throw UnauthorizedLoginException(Constant.LOGIN_UNAUTHORIZED)
            }
        }
        catch (runtimeException: RuntimeException) {
            LoggerFactory.getLogger(AuthenticationManager::class.java).warn("failed to authenticate user, reasons : ${runtimeException.message}")
            return null
        }
        return null
    }

    override fun resetPasswordRequest(username: String) : String {
        return authenticationExternalService.resetPasswordRequest(
            ResetPasswordRequestDto(PROFILE, username)
        )
    }

    override fun updatePassword(passwordForm: ResetPasswordForm): Boolean {

        return try {
            authenticationExternalService.updatePassword(
                UpdatePasswordDto(passwordForm.email!!, passwordForm.password!!, passwordForm.code!!, PROFILE)
            )
            true
        } catch (httpError: HttpClientErrorException) {

            when(httpError.statusCode) {
                HttpStatus.UNAUTHORIZED -> false
                else
                -> throw httpError
            }
        }
    }

}
