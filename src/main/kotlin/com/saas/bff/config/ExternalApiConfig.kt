package com.saas.bff.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class ExternalApiConfig {

    @Value("\${saas.oil.endpoint.url}")
    private lateinit var sassOilEndPointUrl: String

    @Value("\${saas.weather.endpoint.url}")
    private lateinit var sassWeatherEndPointUrl: String

    @Bean
    fun saasOilWebClient(): WebClient {
        return WebClient.create(sassOilEndPointUrl)
    }

    @Bean
    fun sassWeatherWebClient(): WebClient {
        return WebClient.create(sassWeatherEndPointUrl)
    }
}
