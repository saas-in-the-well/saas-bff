package com.saas.bff.api.oil.api

import com.saas.bff.model.RequestModel
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono


@Component
class OilApi (
    private val saasOilWebClient: WebClient
){

    fun getLowTop10(area: String, cnt: String, prodcd: String): Mono<String> {

        return saasOilWebClient.get()
            .uri("/opinet/api/lowTop10?area=$area&cnt=$cnt&prodcd=$prodcd")
            .retrieve()
            .bodyToMono(String::class.java)
    }
}