package com.saas.bff.controller

import com.example.bff.model.ResponseModel
import com.saas.bff.model.RequestModel
import com.saas.bff.Service.GeolocationBffService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/geo")
class GeolocationController {

    @Autowired
    private lateinit var geolocationBffService: GeolocationBffService

    @PostMapping("/location")
    suspend fun geoLocation(@RequestBody request: RequestModel): ResponseEntity<Any> {

        if(request.address.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("address is empty")
        }

        val result = geolocationBffService.geolocationFromApis(request)

        // 처리 결과에 따른 응답 반환
        return if (result == null || result.equals("error")) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result)
        } else {
            ResponseEntity.ok(result)
        }
    }
}