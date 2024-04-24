package com.saas.bff.api.oil.api

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono


@Component
class WeatherApi (
    private val sassWeatherWebClient: WebClient
){

    fun weather(location: String): Mono<String> {
        return sassWeatherWebClient.get()
            .uri("/weather?location=$location")
            .retrieve()
            .bodyToMono(String::class.java)
    }
}