package com.evolutivecode.opccadminportal.application.handler

import com.evolutivecode.opccadminportal.common.util.Constant
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomLogoutHandler : LogoutHandler {

    override fun logout(httpServletRequest: HttpServletRequest, p1: HttpServletResponse?, p2: Authentication?) {
        httpServletRequest.session.removeAttribute(Constant.USER_ROLE)
        httpServletRequest.session.removeAttribute(Constant.USER)
        httpServletRequest.session.invalidate()
    }
}
