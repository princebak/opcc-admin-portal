package com.evolutivecode.opccadminportal.core.service

import com.evolutivecode.opccadminportal.core.domain.Donation
import com.evolutivecode.opccadminportal.core.domain.Credential

interface DonationService{
    fun create(credential: Credential, t: Donation): Donation?
    fun findAll() : List<Donation>
    fun findById(id:String): Donation?
    fun update(credential: Credential, t: Donation): Donation?
    fun count(): Long
    fun findAllByCaseId(caseId:String): List<Donation>
    fun totalDonationsAmount(): Double?
    fun lastDonations(): List<Donation>?
}