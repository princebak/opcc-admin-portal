package com.evolutivecode.opccadminportal.application.controller

import com.evolutivecode.opccadminportal.common.util.Constant.Companion.CREDENTIAL
import com.evolutivecode.opccadminportal.common.util.Constant.Companion.USER_INFO_OBJECT
import com.evolutivecode.opccadminportal.core.service.UserService
import com.evolutivecode.opccadminportal.core.service.DonationService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.SessionAttributes
import java.util.*
import javax.servlet.http.HttpServletRequest


@SessionAttributes(CREDENTIAL, USER_INFO_OBJECT)
@Controller
class DefaultController(var userService: UserService, var donationService: DonationService) {

    private final val logger: Logger = LoggerFactory.getLogger(DefaultController::class.java)

    @RequestMapping("/", "/index")
    fun homeTopup(model : Model, locale: Locale): String {

        logger.info("Get home page user locale ${locale.language}")
        model.addAttribute("title", "home")

        return "home"
    }

    @RequestMapping("/admin-profile")
    fun goToProfile(model : Model, locale: Locale,
                    request: HttpServletRequest): String {
        logger.info("Get home page user locale ${locale.language}")
        model.addAttribute("pageTitle","profile")

        return "admin-profile"
    }

}