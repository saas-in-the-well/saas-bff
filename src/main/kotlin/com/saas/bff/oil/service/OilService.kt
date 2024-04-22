package com.saas.bff.oil.service

import com.saas.bff.model.RequestModel
import com.saas.bff.oil.code.Region
import com.saas.bff.weather.service.WeatherService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactive.awaitSingle
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class OilService (private val saasOilWebClient: WebClient) {

    private val logger = LoggerFactory.getLogger(OilService::class.java)

    suspend fun areaCode(requestModel: RequestModel): String {

        try{
            val region = Region.findCode(requestModel.address)
            logger.info("## call /weather param -> area : [{}]", region.code)

            return saasOilWebClient.get()
                .uri("/opinet/api/areaCode?area="+region.code)
                .retrieve()
                .bodyToMono(String::class.java)
                .awaitSingle()
        }catch(e: Exception){

            e.printStackTrace()
            return "error"
        }

    }

    suspend fun areaAvgRecentPrice(requestModel: RequestModel): String {
        return saasOilWebClient.get()
            .uri("/opinet/api/areaAvgRecentPrice?area=0120&date=20240422&prodcd=B027")
            //.bodyValue(requestModel)
            .retrieve()
            .bodyToMono(String::class.java)
            .awaitSingle()
    }
}