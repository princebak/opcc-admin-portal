package com.evolutivecode.opccadminportal.application.handler

import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.jvm.Throws

@Component
class LogoutRequestHandler : SimpleUrlLogoutSuccessHandler(), LogoutSuccessHandler {
    private val LOGGER = LoggerFactory.getLogger(LogoutRequestHandler::class.java)

    @Throws(IOException::class, ServletException::class)
    override fun onLogoutSuccess(
            request: HttpServletRequest,
            response: HttpServletResponse,
            authentication: Authentication?) {
        val refererUrl = request.getHeader("Referer")
        LOGGER.info("Logout from: $refererUrl")

        super.onLogoutSuccess(request, response, authentication)
    }
}
