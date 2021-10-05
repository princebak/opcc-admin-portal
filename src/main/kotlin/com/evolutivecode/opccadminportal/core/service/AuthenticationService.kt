package com.evolutivecode.opccadminportal.core.service

import com.evolutivecode.opccadminportal.application.form.ResetPasswordForm
import com.evolutivecode.opccadminportal.core.domain.user.User
import com.evolutivecode.opccadminportal.core.domain.user.UserInfo
import org.springframework.security.core.Authentication

interface AuthenticationService {
    fun createUser(user: User)
    fun getUserInfo(token: String): UserInfo
    fun authenticate(authentication: Authentication?): String?
    fun resetPasswordRequest(username: String) : String
    fun updatePassword(passwordForm: ResetPasswordForm): Boolean
}
