package com.saas.bff.Service

import com.example.bff.model.ResponseModel
import com.saas.bff.model.RequestModel
import com.saas.bff.oil.service.OilService
import com.saas.bff.weather.service.WeatherService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Slf4j
@Service
class GeolocationBffService () {

    @Autowired private lateinit var oilService: OilService
    @Autowired private lateinit var weatherService: WeatherService

    suspend fun geolocationFromApis(requestModel: RequestModel): ResponseModel {
        var (oilResponse, weatherResponse) = coroutineScope {
            val oil = async { oilService.areaCode(requestModel) }
            val weather = async { weatherService.weather(requestModel) }
            Pair(oil.await(), weather.await())
        }

        val responseData = HashMap<String, Any>().apply {
            put("oilData", oilResponse)
            put("weatherData", weatherResponse)
        }

        val responseModel = ResponseModel(123, "geolocationFromApis", responseData)

        return responseModel
    }
}