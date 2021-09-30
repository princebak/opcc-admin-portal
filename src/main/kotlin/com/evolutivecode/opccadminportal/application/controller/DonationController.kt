package com.evolutivecode.opccadminportal.application.controller

import com.evolutivecode.opccadminportal.core.service.DonationService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

@Controller
class DonationController(var donationService: DonationService) {

    private val logger: Logger = LoggerFactory.getLogger(DonationController::class.java)

    @RequestMapping("donations")
    fun donation(model: Model, locale: Locale,@RequestParam(name="page",required = false,defaultValue = "1") page : Int):String{
        logger.info("Get home page user locale ${locale.language}")
        model.addAttribute("title", "donations")

        return "donations"
    }

}