package com.saas.bff.api.weather.config

import com.saas.bff.api.weather.service.WeatherService
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WeatherApiConfig {

    @Value("\${saas.weather.endpoint.url}")
    private lateinit var sassWeatherEndPointUrl: String


    @Bean
    fun sassWeatherWebClient(): WebClient {
        return WebClient.create(sassWeatherEndPointUrl)
    }

    @Bean
    fun WeatherCircuitBreakerService(sassWeatherWebClient: WebClient, circuitBreakerOperator: CircuitBreakerOperator<String>): WeatherService {
        return WeatherService(sassWeatherWebClient, circuitBreakerOperator)
    }
}
