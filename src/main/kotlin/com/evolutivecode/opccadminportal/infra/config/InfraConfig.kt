package com.evolutivecode.opccadminportal.infra.config

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder

import org.apache.http.impl.client.HttpClientBuilder
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.retry.annotation.EnableRetry
import org.springframework.retry.backoff.FixedBackOffPolicy
import org.springframework.retry.policy.SimpleRetryPolicy
import org.springframework.retry.support.RetryTemplate
import org.springframework.web.client.RestTemplate

@EnableRetry
@Configuration
class InfraConfig {
    private final val timeout = 10000

    @Value("\${remote.maxTotalConnect}")
    private final val maxTotalConnect: Int = 1

    @Value("\${remote.maxConnectPerRoute}")
    private final val maxConnectPerRoute: Int = 200

    @Value("\${remote.connectTimeout}")
    private final val connectTimeout: Int = 2000

    @Value("\${remote.readTimeout}")
    private final val readTimeout: Int = 3000

    @Value("\${spring.retry.backOffPolicy}")
    private final val backOffPolicy: Long = 1000

    @Value("\${spring.retry.maxAttempts}")
    private final val maxAttempts: Int = 2

    @Value("\${aws.access.key}")
    private val awsAccessKey: String? = null

    @Value("\${aws.secret.key}")
    private val awsSecretKey: String? = null

    private fun initClientHttpRequestFactory() : ClientHttpRequestFactory {
        if (this.maxTotalConnect <= 0) {
            val factory = SimpleClientHttpRequestFactory()
            factory.setReadTimeout(this.readTimeout)
            factory.setConnectTimeout(this.connectTimeout)
            return factory
        }
        val httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(this.maxTotalConnect)
                .setMaxConnPerRoute(this.maxConnectPerRoute).build()
        val factory = HttpComponentsClientHttpRequestFactory(httpClient)
        factory.setConnectTimeout(this.connectTimeout)
        factory.setReadTimeout(this.readTimeout)
        return factory
    }


    private fun initMessageConverter(objectMapper: ObjectMapper): HttpMessageConverter<Any> {
        val httpMessageConverter = MappingJackson2HttpMessageConverter()
        httpMessageConverter.objectMapper = objectMapper
        return httpMessageConverter
    }

    private fun replaceRestTemplateDefaultObjectMapper(restTemplate: RestTemplate, objectMapper: ObjectMapper) {
        val customObjectMapperHttpMessageConverter = initMessageConverter(objectMapper)
        val httpMessageConverters = restTemplate.messageConverters

        for (httpMessageConverter in httpMessageConverters) {
            if (httpMessageConverter is MappingJackson2HttpMessageConverter) {
                httpMessageConverters[httpMessageConverters.indexOf(httpMessageConverter)] = customObjectMapperHttpMessageConverter
            }
        }
    }

    @Bean
    fun configureRestTemplate(objectMapper: ObjectMapper): RestTemplate {
        val restTemplate = RestTemplate(this.initClientHttpRequestFactory())
        replaceRestTemplateDefaultObjectMapper(restTemplate, objectMapper)
        return restTemplate
    }

    @Bean
    fun configureRetryTemplate(): RetryTemplate {
        val retryTemplate = RetryTemplate()
        val fixedBackOffPolicy = FixedBackOffPolicy()
        fixedBackOffPolicy.backOffPeriod = backOffPolicy
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy)

        val retryPolicy = SimpleRetryPolicy()
        retryPolicy.maxAttempts = maxAttempts
        retryTemplate.setRetryPolicy(retryPolicy)

        return retryTemplate
    }


    @Bean
    fun s3Client(): AmazonS3 {
        val credentials: AWSCredentials = BasicAWSCredentials(awsAccessKey, awsSecretKey)
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_WEST_1)
                .build()
    }
}