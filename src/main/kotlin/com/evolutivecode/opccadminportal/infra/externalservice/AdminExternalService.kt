package com.evolutivecode.opccadminportal.infra.externalservice

import com.evolutivecode.opccadminportal.common.util.HttpEntityFactory
import com.evolutivecode.opccadminportal.core.domain.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.retry.RetryCallback
import org.springframework.retry.support.RetryTemplate
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import java.lang.Exception

@Component
class AdminExternalService(
    private val restTemplate: RestTemplate,
    private val retryTemplate: RetryTemplate,
    @Value("\${service.opcc.admin.url}") private val adminBaseUrl: String) {

    private val logger = LoggerFactory.getLogger(AdminExternalService::class.java)
    fun getStatistics(username: String, credential: Credential): Statistics {
        logger.info("get all tenders...")
        val url = adminBaseUrl.plus("/statistics")


        logger.info("url : $url")
        return try{
            retryTemplate.execute(
                RetryCallback<Statistics, RestClientException>{
                    restTemplate.exchange(
                        url, HttpMethod.GET, HttpEntityFactory.buildUserEntityAndIdentity(username, credential),
                        object : ParameterizedTypeReference<Statistics>(){}).body
                }
            )

        }
        catch (e: Exception) {
            logger.error(e.message)
            Statistics()
        }
    }

    fun getDonationsHistory(username: String, credential: Credential): ResponseModel<Donation> {
        logger.info("get all donations...")
        val url = adminBaseUrl.plus("/donations")


        logger.info("url : $url")
        return try{
            retryTemplate.execute(
                RetryCallback<ResponseModel<Donation>, RestClientException>{
                    restTemplate.exchange(
                        url, HttpMethod.GET, HttpEntityFactory.buildUserEntityAndIdentity(username, credential),
                        object : ParameterizedTypeReference<ResponseModel<Donation>>(){}).body
                }
            )

        }
        catch (e: Exception) {
            logger.error(e.message)
            ResponseModel()
        }
    }

    fun getPortalUsers(username : String,  credential: Credential) : ResponseModel<PortalUser>{
        logger.info("get all portal-users...")
        val url = adminBaseUrl.plus("/portal-users")


        logger.info("url : $url")
        return try{
            retryTemplate.execute(
                RetryCallback<ResponseModel<PortalUser>, RestClientException>{
                    restTemplate.exchange(
                        url, HttpMethod.GET, HttpEntityFactory.buildUserEntityAndIdentity(username, credential),
                        object : ParameterizedTypeReference<ResponseModel<PortalUser>>(){}).body
                }
            )

        }
        catch (e: Exception) {
            logger.error(e.message)
            ResponseModel()
        }
    }

    fun getTenders(username: String, credential: Credential): ResponseModel<Tender> {
        logger.info("get all offers...")
        val url = adminBaseUrl.plus("/tenders")


        logger.info("url : $url")
        return try{
            retryTemplate.execute(
                RetryCallback<ResponseModel<Tender>, RestClientException>{
                    restTemplate.exchange(
                        url, HttpMethod.GET, HttpEntityFactory.buildUserEntityAndIdentity(username, credential),
                        object : ParameterizedTypeReference<ResponseModel<Tender>>(){}).body
                }
            )

        }
        catch (e: Exception) {
            logger.error(e.message)
            ResponseModel()
        }
    }
}
