package com.saas.bff.api.oil.service

import com.saas.bff.api.oil.api.OilApi
import com.saas.bff.api.oil.code.Region
import com.saas.bff.model.RequestModel
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class OilService(
    private val saasOilWebClient: WebClient,
    private val circuitBreakerRegistry: CircuitBreakerRegistry) {

    private val logger = LoggerFactory.getLogger(OilService::class.java)
    @Autowired
    private lateinit var oilApi: OilApi

    fun areaCode(requestModel: RequestModel): Mono<String> {

        val region = Region.findCode(requestModel.address)
        return saasOilWebClient.get()
            .uri("/opinet/api/areaCode?area="+region.code)
            //.bodyValue(requestModel)
            .retrieve()
            .bodyToMono(String::class.java)
    }

    fun areaAvgRecentPrice(requestModel: RequestModel): Mono<String> {

        val circuitBreaker = circuitBreakerRegistry.circuitBreaker("oilCircuitBreaker")

        logger.info("## CALL OIL API >> weather param -> 하드코딩.. [0120, 20, B027]")

        return oilApi.getLowTop10("0120", "20", "B027")
            .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
            .onErrorResume {
                Mono.just("@@@@ Fallback response due to error: ${it.message}")
            }
    }
}