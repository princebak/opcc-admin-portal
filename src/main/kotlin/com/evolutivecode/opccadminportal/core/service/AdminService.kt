package com.evolutivecode.opccadminportal.core.service

import com.evolutivecode.opccadminportal.core.domain.Credential
import com.evolutivecode.opccadminportal.core.domain.Statistics

interface AdminService {
    fun getStatistics(username: String, credential: Credential): Statistics
}
