package com.evolutivecode.opccadminportal.application.controller

import com.evolutivecode.opccadminportal.common.util.Constant
import com.evolutivecode.opccadminportal.core.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.RequestContextUtils
import java.util.*
import javax.servlet.http.HttpServletRequest

@Controller
class ClientController (var userService: UserService){

    private final val logger: Logger = LoggerFactory.getLogger(ClientController::class.java)

    @RequestMapping("clients")
    fun medicalInstitute(model: Model, locale: Locale,request: HttpServletRequest,
                         @RequestParam(name="page",required = false,defaultValue = "1") page : Int):String{
        logger.info("Get home page user locale ${locale.language}")
        model.addAttribute(Constant.PAGE_TITLE, "clients")

        return "clients"
    }

    @RequestMapping("/create-client")
    fun loadCreateClient(model: Model, locale: Locale,request: HttpServletRequest): String{
        logger.info("Get home page user locale ${locale.language}")
        var operation = request.getParameter("operation")+""
        var clientId = request.getParameter("clientId")+""
        val locale: Locale = RequestContextUtils.getLocale(request)
        val lang:String = locale.displayLanguage

        return "create-client"

    }


    @RequestMapping("/client")
    fun loadCase(model: Model, locale: Locale,@RequestParam id : String,request: HttpServletRequest): String{
        logger.info("Get home page user locale ${locale.language}")
        model.addAttribute(Constant.PAGE_TITLE, "client")

        return "client"
    }

}