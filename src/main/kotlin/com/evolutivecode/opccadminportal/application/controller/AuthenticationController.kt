package com.evolutivecode.opccadminportal.application.controller

import com.evolutivecode.opccadminportal.common.util.Constant
import com.evolutivecode.opccadminportal.common.util.PageDetails
import com.evolutivecode.opccadminportal.core.service.AuthenticationService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import java.util.*
import javax.servlet.http.HttpServletRequest

@Controller
class AuthenticationController(private val authenticationService : AuthenticationService) {


    @GetMapping("/login")
    fun login(local : Locale) : String{

        return "authentication/login"
    }

    @GetMapping("/reset-password")
    fun resetPassword(local : Locale) : String{

        return "authentication/reset"
    }

    @ModelAttribute(Constant.PAGE_DETAILS)
    fun getPageDetails(request : HttpServletRequest) : PageDetails{
        return PageDetails()
    }
    
}
