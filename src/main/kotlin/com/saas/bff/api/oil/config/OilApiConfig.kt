package com.saas.bff.api.oil.config

import com.saas.bff.api.oil.service.OilService
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class OilApiConfig {

    @Value("\${saas.oil.endpoint.url}")
    private lateinit var sassOilEndPointUrl: String

    @Bean
    fun saasOilWebClient(): WebClient {
        return WebClient.create(sassOilEndPointUrl)
    }

    @Bean
    fun oilCircuitBreakerService(saasOilWebClient: WebClient, circuitBreakerOperator: CircuitBreakerOperator<String>): OilService {
        return OilService(saasOilWebClient, circuitBreakerOperator)
    }

}
