package com.evolutivecode.opccadminportal.infra.externalservice

import com.evolutivecode.opccadminportal.common.util.Constant
import com.evolutivecode.opccadminportal.common.util.HttpEntityFactory
import com.evolutivecode.opccadminportal.core.domain.Credential
import com.evolutivecode.opccadminportal.core.domain.Donation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.retry.RetryCallback
import org.springframework.retry.support.RetryTemplate
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

@Component
class DonationExternalService(
        private val restTemplate: RestTemplate,
        private val retryTemplate: RetryTemplate,
        @Value("\${service.sgc.donations.url}") private val donationBaseUrl: String
){

    private final val logger: Logger = LoggerFactory.getLogger(DonationExternalService::class.java)
    var donations:ArrayList<Donation> = ArrayList()

     fun create(credential: Credential, t: Donation): Donation? {
        val request = HttpEntity(t, HttpEntityFactory.buildUserEntity(credential).headers)
        return try { restTemplate.postForObject(donationBaseUrl, request, t::class.java)}
        catch(ex : Exception){
            logger.warn(Constant.UNABLE_TO_CONNECT_TO_BACKEND)
            throw Exception()
        }
    }

     fun findAll(): List<Donation> {
        logger.info("get all Donations...")
        val url = donationBaseUrl.plus("/list")
        logger.info("url : $url")
        return try {
            retryTemplate.execute(
                    RetryCallback<List<Donation>, RestClientException> {
                        restTemplate.exchange(
                                url, HttpMethod.GET, HttpEntityFactory.buildNoUserEntity(),
                                object : ParameterizedTypeReference<List<Donation>>() {}).body
                    }
            )
        }catch(ex : Exception){
            logger.warn(Constant.UNABLE_TO_CONNECT_TO_BACKEND)
            return throw Exception() //ResponseModel()
        }
    }

     fun findById(donationId: String): Donation? {
        val url = donationBaseUrl.plus("/donationId")
        return try {
            retryTemplate.execute(
                    RetryCallback<Donation, Exception> {
                        restTemplate.exchange(
                                url, HttpMethod.GET, HttpEntityFactory.buildUserEntityWithField("donationId", donationId),
                                object : ParameterizedTypeReference<Donation>() {}).body
                    }
            )
        }catch(ex : Exception){
            logger.warn(Constant.UNABLE_TO_CONNECT_TO_BACKEND)
            return throw Exception()
        }
    }

     fun update(credential: Credential, t: Donation): Donation? {
        logger.info("update Donation with ID : ${t.id}")
        val request = HttpEntity(t, HttpEntityFactory.buildUserEntity(credential).headers)
        return try{
            restTemplate.postForObject(donationBaseUrl.plus("/update"), request, Donation::class.java)
        }catch(ex : Exception){
            logger.warn(Constant.UNABLE_TO_CONNECT_TO_BACKEND)
            throw Exception()
        }
    }
    fun findAllByCaseId(caseId: String): List<Donation> {
        logger.info("get all DonationCases by donationCaseId = ${caseId}")
        val url = donationBaseUrl.plus("/donation-case")
        logger.info("url : $url")

        return try {
            retryTemplate.execute(
                    RetryCallback<List<Donation>, RestClientException> {
                        restTemplate.exchange(
                                url, HttpMethod.GET, HttpEntityFactory.buildUserEntityWithField("caseId", caseId),
                                object : ParameterizedTypeReference<List<Donation>>() {}).body
                    }
            )
        }catch(ex : Exception){
            logger.info(Constant.UNABLE_TO_CONNECT_TO_BACKEND + ex.message)
            return throw Exception() //ResponseModel()
        }
    }

     fun count(): Long {
        logger.info("count all Donations...")
        val url = donationBaseUrl.plus("/count")
        logger.info("url : $url")
        return try {
            retryTemplate.execute(
                    RetryCallback<Long, RestClientException> {
                        restTemplate.exchange(
                                url, HttpMethod.GET, HttpEntityFactory.buildNoUserEntity(),
                                object : ParameterizedTypeReference<Long>() {}).body
                    }
            )
        }catch(ex : Exception){
            logger.warn(Constant.UNABLE_TO_CONNECT_TO_BACKEND)
            return throw Exception()
        }
    }

    fun totalDonationsAmount(): Double?{
        logger.info("getTotalDonationsAmount all Donations...")
        val url = donationBaseUrl.plus("/total-donations-amount")
        logger.info("url : $url")
        return try {
            retryTemplate.execute(
                    RetryCallback<Double, RestClientException> {
                        restTemplate.exchange(
                                url, HttpMethod.GET, HttpEntityFactory.buildNoUserEntity(),
                                object : ParameterizedTypeReference<Double>() {}).body
                    }
            )
        }catch(ex : Exception){
            logger.warn(Constant.UNABLE_TO_CONNECT_TO_BACKEND)
            return throw Exception()
        }
    }
    fun lastDonations(): List<Donation>?{
        logger.info("get lastDonations...")
        val url = donationBaseUrl.plus("/last-donations")
        logger.info("url : $url")
        return try {
            retryTemplate.execute(
                    RetryCallback<List<Donation>, RestClientException> {
                        restTemplate.exchange(
                                url, HttpMethod.GET, HttpEntityFactory.buildNoUserEntity(),
                                object : ParameterizedTypeReference<List<Donation>>() {}).body
                    }
            )
        }catch(ex : Exception){
            logger.warn(Constant.UNABLE_TO_CONNECT_TO_BACKEND)
            return throw Exception()
        }
    }
}