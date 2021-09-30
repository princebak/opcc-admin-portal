package com.evolutivecode.opccadminportal.common.util

class Constant {
    companion object {
        const val UPDATE_PASSWORD_FORM = "resetPwdForm"
        const val AUTH_PROFILE: String = "sgc"
        const val SIGN_UP_FAILED: String = "signup failed"
        const val UNEXPECTED_ERROR: String = "unexpected error"
        const val LOGIN_FAILED = "Login Failed"
        const val LOGIN_UNAUTHORIZED = "Login Unauthorized"

        const val PAGE_TITLE = "pageTitle"
        const val PAGE_SLUG: String = "pageSlug"

        const val CONTACT_FORM: String = "contactForm"
        const val REGISTER_FORM: String = "registerForm"
        const val DONATOR_FORM = "donatorForm"
        const val CLIENT_FORM = "clientForm"
        const val DONATION_CASE_FORM = "donationCaseForm"

        const val X_JWT = "X-Jwt"
        const val X_API_KEY = "X-Api-Key"
        const val X_APP_ID = "X-App-Id"
        const val TOKEN: String = "token"
        const val USER_INFO_OBJECT: String = "user"
        const val CREDENTIAL = "credential"

        const val USER_ROLE: String = "user-role"
        const val USER: String = "user"
        const val AUTHOR: String = "author"

        const val USER_PROFILE_OBJECT: String = "profile"

        const val DONATION_CASE_CAT_1 = "All"
        const val DONATION_CASE_CAT_2 = "Patient"
        const val DONATION_CASE_CAT_3 = "Platform"
        const val DONATION_CASE_CAT_4 = "Clinic"
        const val DONATION_CASE_CAT_5 = "StaffTraining"
        const val DONATION_CASE_CAT_6 = "CrowdFunding"
        const val CATEGORY_TAB = "categoryTab"

        // Messages classes
        const val SUCCESS = "alert-success"
        const val INFO = "alert-info"
        const val DANGER = "alert-danger"

        const val GET_RESET_CODE = "getResetCode"
        const val UPDATE_CREDENTIALS = "updateCredentials"

        // Returning Paths
        const val REDIRECT_RESET_CONFIRM= "redirect:/reset-confirm"

        const val INVALID_SESSION = "invalid session"
        const val NOT_YET_IMPLEMENTED = "Not yet implemented"
        const val UNABLE_TO_CONNECT_TO_BACKEND = "error occurred : unable to connect the remote server"

    }
}