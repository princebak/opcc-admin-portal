package com.evolutivecode.opccadminportal.application.handler

import com.evolutivecode.opccadminportal.common.util.Constant.Companion.CREDENTIAL
import com.evolutivecode.opccadminportal.core.service.AuthenticationService
import com.evolutivecode.opccadminportal.common.util.Constant.Companion.TOKEN
import com.evolutivecode.opccadminportal.common.util.Constant.Companion.USER_INFO_OBJECT
import com.evolutivecode.opccadminportal.core.domain.Credential
import javassist.NotFoundException
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomAuthenticationSuccessHandler(private val authenticationService: AuthenticationService): SimpleUrlAuthenticationSuccessHandler() {

    private val requestCache = HttpSessionRequestCache()

    override fun onAuthenticationSuccess(request: HttpServletRequest,
                                         response: HttpServletResponse,
                                         authentication: Authentication){
        setUseReferer(true)
        val savedRequest = requestCache.getRequest(request,response)

        val token = (authentication.principal as User).username
        val userInfo = authenticationService.getUserInfo(token)
        val credential = Credential(token, userInfo.access!!)

        request.session.setAttribute(TOKEN,token)
        request.session.setAttribute(USER_INFO_OBJECT,userInfo)
        request.session.setAttribute(CREDENTIAL, credential)

        logger.info("user ${userInfo.email} authenticated successfully")

        try {

            if(savedRequest == null){
                response.sendRedirect("/dashboard")
                super.onAuthenticationSuccess(request, response, authentication)
                return
            }

            if(isAlwaysUseDefaultTargetUrl || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))){
                requestCache.removeRequest(request,response)
                super.onAuthenticationSuccess(request, response, authentication)
                return
            }

            clearAuthenticationAttributes(request)
            val targetUrl: String = if (savedRequest.redirectUrl.contains("sw.js"))  "/my-profile" else savedRequest.redirectUrl

            if(targetUrl.endsWith("/error")){
                targetUrl.replace("/error", "/my-profile")
            }

            response.sendRedirect(targetUrl)

        }
        catch (e: NotFoundException) {
            logger.error(e.message)
            redirectStrategy.sendRedirect(request,response, "/?error=true")
        }
    }
}
