package com.saas.bff.api.weather.service

import com.saas.bff.model.RequestModel
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator
import kotlinx.coroutines.reactive.awaitSingle
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class WeatherService(
    private val sassWeatherWebClient: WebClient,
    private val circuitBreakerOperator: CircuitBreakerOperator<String>
) {

    private val logger = LoggerFactory.getLogger(WeatherService::class.java)

    suspend fun weather(requestModel: RequestModel): String? {

        var split = requestModel.address.split(" ")
        var city = split[0]
        var district = if (split.size > 1) split[1] else ""
        var location = city + " " + district

        logger.info("## CALL WEATHER API >> weather param -> location : [{}]", location)

        val resultMono = sassWeatherWebClient.get()
            .uri("/weather?location="+ location)
            //.bodyValue(requestModel)
            .retrieve()
            .bodyToMono(String::class.java)

        return circuitBreakerOperator
            .run {
                resultMono.map { result ->
                    // Circuit Breaker가 열려있지 않으면 이 부분이 실행됩니다.
                    result
                }
            }
            .onErrorReturn("error")
            .block()

    }
}