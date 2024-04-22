package com.saas.bff.config

import com.saas.bff.api.oil.service.OilService
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import java.time.Duration

@Configuration
class ExternalApiConfig {

    @Bean
    fun circuitBreakerOperator(): CircuitBreakerOperator<String> {
        val circuitBreakerConfig = CircuitBreakerConfig.custom()
            .failureRateThreshold(50.0f) // 실패 비율 임계값 설정
            .waitDurationInOpenState(Duration.ofSeconds(3)) // 오픈 상태에서 대기 시간 설정
            .slidingWindowSize(5) // 슬라이딩 창 크기 설정
            .build()

        val circuitBreakerRegistry = io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry.of(circuitBreakerConfig)

        return CircuitBreakerOperator.of(circuitBreakerRegistry.circuitBreaker("bffCircuitBreaker"))
    }
}
