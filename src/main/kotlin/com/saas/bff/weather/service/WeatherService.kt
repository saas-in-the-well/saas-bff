package com.saas.bff.weather.service

import com.saas.bff.Service.GeolocationBffService
import com.saas.bff.model.RequestModel
import com.saas.bff.oil.code.Region
import com.saas.bff.oil.service.OilService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactive.awaitSingle
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class WeatherService (private val sassWeatherWebClient: WebClient) {

    private val logger = LoggerFactory.getLogger(WeatherService::class.java)

    suspend fun weather(requestModel: RequestModel): String {

        try {
            var split = requestModel.address.split(" ")
            var city = split[0]
            var district = if (split.size > 1) split[1] else ""
            var location = city + " " + district

            logger.info("## call /weather param -> location : [{}]", location)

            return sassWeatherWebClient.get()
                .uri("/weather?location="+ location)
                //.bodyValue(requestModel)
                .retrieve()
                .bodyToMono(String::class.java)
                .awaitSingle()

        }catch (e: Exception){
            e.printStackTrace()
            return "error"
        }

    }
}