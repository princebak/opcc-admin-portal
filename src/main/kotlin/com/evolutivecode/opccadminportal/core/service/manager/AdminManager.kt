package com.evolutivecode.opccadminportal.core.service.manager

import com.evolutivecode.opccadminportal.core.domain.*
import com.evolutivecode.opccadminportal.core.service.AdminService
import com.evolutivecode.opccadminportal.infra.externalservice.AdminExternalService
import org.springframework.stereotype.Service

@Service
class AdminManager(private val adminExternalService : AdminExternalService) : AdminService {
    override fun getStatistics(username: String, credential: Credential): Statistics {
        return adminExternalService.getStatistics(username, credential)
    }

    override fun getDonationsHistory(username: String, credential: Credential): ResponseModel<Donation> {
        return adminExternalService.getDonationsHistory(username, credential)
    }

    override fun getPortalUsers(username: String, credential: Credential): ResponseModel<PortalUser> {
        return adminExternalService.getPortalUsers(username, credential)
    }

    override fun getTenders(username: String, credential: Credential): ResponseModel<Tender> {
        return adminExternalService.getTenders(username, credential)
    }
}
