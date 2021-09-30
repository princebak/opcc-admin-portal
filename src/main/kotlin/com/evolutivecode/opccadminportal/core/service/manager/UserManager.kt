package com.evolutivecode.opccadminportal.core.service.manager

import com.evolutivecode.opccadminportal.core.domain.User
import com.evolutivecode.opccadminportal.core.domain.Credential
import com.evolutivecode.opccadminportal.core.service.UserService
import com.evolutivecode.opccadminportal.infra.externalservice.UserExternalService
import org.springframework.stereotype.Service

@Service
class UserManager(var userExternalService: UserExternalService): UserService {
    override fun create(credential: Credential, t: User): User? {
        return userExternalService.create(credential, t!!)
    }

    override fun findAll(): List<User> {
        return userExternalService.findAll()
    }

    override fun findById(id: String): User? {
        return userExternalService.findById(id!!)
    }

    override fun update(credential: Credential, t: User): User? {
        return userExternalService.update(credential, t!!)
    }
    override fun count(): Long {
        return userExternalService.count()
    }
}