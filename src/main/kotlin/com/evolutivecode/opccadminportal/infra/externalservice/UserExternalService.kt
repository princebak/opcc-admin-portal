package com.evolutivecode.opccadminportal.infra.externalservice

import com.evolutivecode.opccadminportal.common.util.Constant
import com.evolutivecode.opccadminportal.common.util.HttpEntityFactory
import com.evolutivecode.opccadminportal.core.domain.User
import com.evolutivecode.opccadminportal.core.domain.Credential
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
class UserExternalService(
        private val restTemplate: RestTemplate,
        private val retryTemplate: RetryTemplate,
        @Value("\${service.sgc.clients.url}") private val recipientBaseUrl: String
){

    private final val logger: Logger = LoggerFactory.getLogger(UserExternalService::class.java)

      fun findAll(): List<User> {
        logger.info("get all Recipients...")
        val url = recipientBaseUrl.plus("/list")
        logger.info("url : $url")
        return try {
            retryTemplate.execute(
                    RetryCallback<List<User>, RestClientException> {
                        restTemplate.exchange(
                                url, HttpMethod.GET, HttpEntityFactory.buildNoUserEntity(),
                                object : ParameterizedTypeReference<List<User>>() {}).body
                    }
            )
        }catch(ex : Exception){
            logger.warn("error occurred when connecting to the remote backend..")
            return throw Exception() //ResponseModel()
        }
    }

     fun findById(clientId: String): User? {
        val url = recipientBaseUrl.plus("/clientId")
        return try {
            retryTemplate.execute(
                    RetryCallback<User, Exception> {
                        restTemplate.exchange(
                                url, HttpMethod.GET, HttpEntityFactory.buildUserEntityWithField("clientId", clientId),
                                object : ParameterizedTypeReference<User>() {}).body
                    }
            )
        }catch(ex : Exception){
            logger.warn(Constant.UNABLE_TO_CONNECT_TO_BACKEND)
            return throw Exception()
        }
    }

     fun create(credential: Credential, t: User): User? {
        val request = HttpEntity(t, HttpEntityFactory.buildUserEntity(credential).headers)
        return try { restTemplate.postForObject(recipientBaseUrl, request, t::class.java)}
        catch(ex : Exception){
            logger.warn(Constant.UNABLE_TO_CONNECT_TO_BACKEND+ex.message)
            throw Exception()
        }
    }

     fun update(credential: Credential, t: User): User? {
        logger.info("update DonationCase with ID : ${t.id}")
        val request = HttpEntity(t, HttpEntityFactory.buildUserEntity(credential).headers)
       return try{
            restTemplate.postForObject(recipientBaseUrl.plus("/update"), request, User::class.java)
        }catch(ex : Exception){
            logger.warn(Constant.UNABLE_TO_CONNECT_TO_BACKEND)
           throw Exception()
        }
    }
     fun count(): Long {
        logger.info("count all Recipients...")
        val url = recipientBaseUrl.plus("/count")
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
}