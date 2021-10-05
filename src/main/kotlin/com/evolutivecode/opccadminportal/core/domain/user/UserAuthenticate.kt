package com.evolutivecode.opccadminportal.core.domain.user

import com.evolutivecode.opccadminportal.common.util.Constant

class UserAuthenticate(username: String, password: String) {
    var username: String? = username
    var password: String? = password

    var profile: String = Constant.AUTH_PROFILE
}
