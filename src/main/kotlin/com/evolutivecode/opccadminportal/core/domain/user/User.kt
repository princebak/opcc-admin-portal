package com.evolutivecode.opccadminportal.core.domain.user

import com.evolutivecode.opccadminportal.common.util.Constant

class User (
        var first: String,
        var last: String,
        //var name: String,
        var email: String,
        var password: String,
        var phone: String,
        var type: String
){
    var profile: String = Constant.AUTH_PROFILE
}
