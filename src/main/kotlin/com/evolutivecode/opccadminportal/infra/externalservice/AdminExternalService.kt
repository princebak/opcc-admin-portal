package com.evolutivecode.opccadminportal.infra.externalservice

import com.evolutivecode.opccadminportal.core.domain.Credential
import com.evolutivecode.opccadminportal.core.domain.Statistics
import org.springframework.stereotype.Component

@Component
class AdminExternalService {
    fun getStatistics(username: String, credential: Credential): Statistics {
        return Statistics()
    }
}
