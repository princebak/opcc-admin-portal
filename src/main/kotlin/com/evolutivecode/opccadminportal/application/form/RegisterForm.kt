package com.evolutivecode.opccadminportal.application.form

import com.evolutivecode.opccadminportal.core.domain.user.User
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class RegisterForm {

    @NotNull
    @Size(min=2, max=60)
    var firstName: String? = null

    @NotNull
    @Size(min=2, max=60)
    var lastName: String? = null

    var email: String? = null

    @NotNull
    @Size(min=6, max=30)
    var password: String? = null

    @NotNull
    @Size(min=6, max=30)
    var phone: String? = null

    var type : AccountType? = AccountType.BUSINESS

    enum class AccountType {
        NOT_SET,
        BUSINESS,
        FREELANCER,
        STUDENT
    }

    fun extractUser(): User {
        return User(this.firstName!!, this.lastName!!, this.email!!, this.password!!, this.phone!!, this.type.toString())
    }

}
