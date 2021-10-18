package com.evolutivecode.opccadminportal.application.handler

import com.evolutivecode.opccadminportal.common.exception.InternalServerException
import com.evolutivecode.opccadminportal.common.exception.InvalidMemberException
import com.evolutivecode.opccadminportal.common.exception.InvalidSessionException
import com.evolutivecode.opccadminportal.common.exception.LoginFailedException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class GlobalExceptionHandler {

    private val logger: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(InvalidSessionException::class)
    fun handleInvalidSessionException(request: HttpServletRequest): ModelAndView {
        logger.error("invalid session exception")
        request.session.invalidate()
        val modelAndView = ModelAndView()
        modelAndView.viewName = "redirect:/login?session=true"
        return modelAndView
    }
    @ExceptionHandler(InvalidMemberException::class)
    fun handleInvalidMemberException(request: HttpServletRequest): ModelAndView {
        logger.error("no such member present in the session variable while an mapping is need it at endpoint ${request.requestURI}")
        val modelAndView = ModelAndView()
        modelAndView.viewName = "redirect:/my-profile?memberID=true"
        return modelAndView
    }

    @ExceptionHandler(InternalServerException::class)
    fun handleInternalServerError(request: HttpServletRequest): ModelAndView {
        logger.error("error 500 : ${request.headerNames.toList()}")
        request.headerNames.toList()
        val modelAndView = ModelAndView()
        modelAndView.viewName = "redirect:/error?code=503"
        return modelAndView
    }

    @ExceptionHandler(ServletRequestBindingException::class)
    fun handleBadRequestException(request: HttpServletRequest) : ModelAndView{
        val modelAndView = ModelAndView()
        modelAndView.viewName = "redirect:/login"
        return modelAndView
    }


}
