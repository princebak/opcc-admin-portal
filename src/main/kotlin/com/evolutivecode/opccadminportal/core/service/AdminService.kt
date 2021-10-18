package com.evolutivecode.opccadminportal.core.service

import com.evolutivecode.opccadminportal.core.domain.*

interface AdminService {
    fun getStatistics(username: String, credential: Credential): Statistics
    fun getDonationsHistory(email: String, credential: Credential): ResponseModel<Donation>
    fun getPortalUsers(username: String, credential: Credential): ResponseModel<PortalUser>
    fun getTenders(username: String, credential: Credential): ResponseModel<Tender>
}
