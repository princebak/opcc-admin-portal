package com.evolutivecode.opccadminportal.core.service.manager

import com.evolutivecode.opccadminportal.core.domain.Credential
import com.evolutivecode.opccadminportal.core.domain.Donation
import com.evolutivecode.opccadminportal.core.service.DonationService
import com.evolutivecode.opccadminportal.infra.externalservice.DonationExternalService
import org.springframework.stereotype.Service

@Service
class DonationManager(
        val donationExternalService: DonationExternalService
): DonationService{
    override fun create(credential: Credential, t: Donation): Donation? {
        return donationExternalService.create(credential, t)
    }

    override fun findAll(): List<Donation> {
        return donationExternalService.findAll()
    }

    override fun findById(id: String): Donation? {
        return donationExternalService.findById(id!!)
    }

    override fun update(credential: Credential, t: Donation): Donation? {
        return donationExternalService.update(credential, t)
    }

    override fun findAllByCaseId(caseId: String): List<Donation> {
        return donationExternalService.findAllByCaseId(caseId)
    }
    override fun count(): Long {
        return donationExternalService.count()
    }

    override fun totalDonationsAmount(): Double? {
        return donationExternalService.totalDonationsAmount()
    }

    override fun lastDonations(): List<Donation>? {
        return donationExternalService.lastDonations()
    }
}