package com.saas.bff.api.weather.service

import com.saas.bff.api.oil.api.WeatherApi
import com.saas.bff.model.RequestModel
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class WeatherService(
    private val sassWeatherWebClient: WebClient,
    private val circuitBreakerRegistry: CircuitBreakerRegistry
) {

    @Autowired
    private lateinit var weatherApi: WeatherApi

    private val logger = LoggerFactory.getLogger(WeatherService::class.java)

    fun weather(requestModel: RequestModel): Mono<String> {

        val weatherCircuitBreaker = circuitBreakerRegistry.circuitBreaker("weatherCircuitBreaker")

        val split = requestModel.address.split(" ")
        val city = split[0]
        val district = if (split.size > 1) split[1] else ""
        val location = city + " " + district

        logger.info("## CALL WEATHER API >> weather param -> location : [{}]", location)

        return weatherApi.weather(location)
            .transformDeferred(CircuitBreakerOperator.of(weatherCircuitBreaker))
            .onErrorResume {
                Mono.just("@@@@ Fallback response due to error: ${it.message}")
            }

    }
}