package com.evolutivecode.opccadminportal.application.controller

import com.evolutivecode.opccadminportal.common.util.Constant
import com.evolutivecode.opccadminportal.common.util.Constant.Companion.CREDENTIAL
import com.evolutivecode.opccadminportal.common.util.Constant.Companion.PAGE_SLUG_DASHBOARD
import com.evolutivecode.opccadminportal.common.util.Constant.Companion.PAGE_SLUG_DONATIONS
import com.evolutivecode.opccadminportal.common.util.Constant.Companion.PAGE_SLUG_PROFILE
import com.evolutivecode.opccadminportal.common.util.Constant.Companion.PAGE_SLUG_SUBSCRIBERS
import com.evolutivecode.opccadminportal.common.util.Constant.Companion.PAGE_SLUG_TENDERS
import com.evolutivecode.opccadminportal.common.util.Constant.Companion.PAGE_TITLE_DASHBOARD
import com.evolutivecode.opccadminportal.common.util.Constant.Companion.PAGE_TITLE_DONATIONS
import com.evolutivecode.opccadminportal.common.util.Constant.Companion.PAGE_TITLE_PROFILE
import com.evolutivecode.opccadminportal.common.util.Constant.Companion.PAGE_TITLE_SUBSCRIBERS
import com.evolutivecode.opccadminportal.common.util.Constant.Companion.PAGE_TITLE_TENDERS
import com.evolutivecode.opccadminportal.common.util.Constant.Companion.STATISTICS
import com.evolutivecode.opccadminportal.common.util.Constant.Companion.USER_INFO_OBJECT
import com.evolutivecode.opccadminportal.common.util.PageDetails
import com.evolutivecode.opccadminportal.core.domain.Credential
import com.evolutivecode.opccadminportal.core.domain.user.UserInfo
import com.evolutivecode.opccadminportal.core.service.AdminService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.SessionAttribute
import org.springframework.web.bind.annotation.SessionAttributes
import javax.servlet.http.HttpServletRequest

@Controller
@SessionAttributes(USER_INFO_OBJECT, CREDENTIAL)
class AdminController(private val adminService : AdminService) {

    @GetMapping("/dashboard")
    fun dashboard(model : Model,
                  @SessionAttribute(USER_INFO_OBJECT) userInfo: UserInfo,
                  @SessionAttribute(CREDENTIAL) credential : Credential ): String{

        val statistics = adminService.getStatistics(userInfo.email!!, credential)
        model.addAttribute(STATISTICS, statistics)

        return "admin/dashboard"
    }

    @GetMapping("/my-profile")
    fun myProfile(model : Model) : String{
        return "admin/profile"
    }

    @GetMapping("/subscribers")
    fun portalUsers(model : Model,
                    @SessionAttribute(USER_INFO_OBJECT) userInfo: UserInfo,
                    @SessionAttribute(CREDENTIAL) credential : Credential ) : String{

        val portalUsers = adminService.getPortalUsers(userInfo.email!!, credential)
        model.addAttribute(Constant.PORTAL_USERS, portalUsers)
        return "admin/portal-users"
    }

    @GetMapping("/donations")
    fun donations(model : Model,
                  @SessionAttribute(USER_INFO_OBJECT) userInfo: UserInfo,
                  @SessionAttribute(CREDENTIAL) credential : Credential ) : String{

        val donations = adminService.getDonationsHistory(userInfo.email!!, credential)
        model.addAttribute(Constant.DONATIONS , donations)
        return "admin/donations"
    }

    @GetMapping("/tenders")
    fun publishedOffers(model : Model,
                  @SessionAttribute(USER_INFO_OBJECT) userInfo: UserInfo,
                  @SessionAttribute(CREDENTIAL) credential : Credential ) : String{

        val tenders = adminService.getTenders(userInfo.email!!, credential)
        model.addAttribute(Constant.TENDERS , tenders)
        return "admin/offers"
    }


    @ModelAttribute(Constant.PAGE_DETAILS)
    fun getPageDetails(request : HttpServletRequest) : PageDetails {

        val pageDetails = PageDetails()

        pageDetails.slug = request.servletPath.substring(1)

        when(pageDetails.slug){
            PAGE_SLUG_DASHBOARD -> {
                pageDetails.title = PAGE_TITLE_DASHBOARD
            }
            PAGE_SLUG_DONATIONS -> {
                pageDetails.title = PAGE_TITLE_DONATIONS
            }
            PAGE_SLUG_SUBSCRIBERS -> {
                pageDetails.title = PAGE_TITLE_SUBSCRIBERS
            }
            PAGE_SLUG_TENDERS -> {
                pageDetails.title = PAGE_TITLE_TENDERS
            }
            PAGE_SLUG_PROFILE -> {
                pageDetails.title = PAGE_TITLE_PROFILE
            }
        }
        return pageDetails
    }


}
