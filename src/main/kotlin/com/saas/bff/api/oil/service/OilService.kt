package com.saas.bff.api.oil.service

import com.saas.bff.api.oil.code.Region
import com.saas.bff.model.RequestModel
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class OilService(
    private val saasOilWebClient: WebClient,
    private val circuitBreakerOperator: CircuitBreakerOperator<String>) {

    private val logger = LoggerFactory.getLogger(OilService::class.java)

    suspend fun areaCode(requestModel: RequestModel): String? {

        val region = Region.findCode(requestModel.address)
        val resultMono = saasOilWebClient.get()
            .uri("/opinet/api/areaCode?area="+region.code)
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

    suspend fun areaAvgRecentPrice(requestModel: RequestModel): String? {

        val resultMono = saasOilWebClient.get()
            .uri("/opinet/api/lowTop10?area=0120&cnt=20&prodcd=B027")
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