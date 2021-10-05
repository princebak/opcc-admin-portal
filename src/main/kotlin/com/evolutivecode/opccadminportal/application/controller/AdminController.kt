package com.evolutivecode.opccadminportal.application.controller

import com.evolutivecode.opccadminportal.common.util.Constant
import com.evolutivecode.opccadminportal.core.domain.Credential
import com.evolutivecode.opccadminportal.core.domain.user.UserInfo
import com.evolutivecode.opccadminportal.core.service.AdminService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.SessionAttribute

@Controller
class AdminController(private val adminService : AdminService) {

    @GetMapping("/dashboard")
    fun dashboard(model : Model,
                  @SessionAttribute(Constant.USER_INFO_OBJECT) userInfo : UserInfo,
                  @SessionAttribute(Constant.CREDENTIAL) credential : Credential): String{

        val statistics = adminService.getStatistics(userInfo.email!!, credential)
        model.addAttribute(Constant.STATISTICS, statistics)

        return "admin/dashboard"
    }

    @GetMapping("/my-profile")
    fun myProfile(model : Model) : String{
        return "admin/profile"
    }
}
