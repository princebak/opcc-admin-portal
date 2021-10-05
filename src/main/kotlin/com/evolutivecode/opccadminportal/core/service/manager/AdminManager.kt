package com.evolutivecode.opccadminportal.core.service.manager

import com.evolutivecode.opccadminportal.core.domain.Credential
import com.evolutivecode.opccadminportal.core.domain.Statistics
import com.evolutivecode.opccadminportal.core.service.AdminService
import com.evolutivecode.opccadminportal.infra.externalservice.AdminExternalService
import org.springframework.stereotype.Service

@Service
class AdminManager(private val adminExternalService : AdminExternalService) : AdminService {
    override fun getStatistics(username: String, credential: Credential): Statistics {
        return adminExternalService.getStatistics(username, credential)
    }
}
