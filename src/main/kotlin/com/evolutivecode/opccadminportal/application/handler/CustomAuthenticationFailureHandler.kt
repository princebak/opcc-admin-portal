package com.evolutivecode.opccadminportal.application.handler

import com.evolutivecode.opccadminportal.common.util.Constant
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomAuthenticationFailureHandler: AuthenticationFailureHandler {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun onAuthenticationFailure(request: HttpServletRequest?, response: HttpServletResponse, exception: AuthenticationException) {
        logger.debug("user login failed")

        when(exception.message) {
            Constant.LOGIN_FAILED -> {
                logger.warn("failed to authentify user ")
                response.sendRedirect(request!!.contextPath + "/login?error=true")
//                request.session.setAttribute("error", true)
            }
            Constant.LOGIN_UNAUTHORIZED -> {
                response.sendRedirect(request!!.contextPath + "/login?confirm=true")
            }
            else -> response.sendRedirect(request!!.contextPath + "/error")
        }
    }
}
