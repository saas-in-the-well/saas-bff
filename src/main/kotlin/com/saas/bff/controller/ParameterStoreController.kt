package com.saas.bff.controller

import com.saas.bff.Service.ParameterStoreService
import com.saas.bff.model.RequestParameterStoreModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/ssm")
class ParameterStoreController(
    private val parameterStoreService: ParameterStoreService
) {
    @PostMapping("/")
    fun getParameterStoreValue(@RequestBody request: RequestParameterStoreModel): ResponseEntity<Any> {

        if(request.parameterName.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("parameterName is empty")
        }

        val result = parameterStoreService.getParameterStoreValue(request)

        // 처리 결과에 따른 응답 반환
        return if (result == null || result.equals("error")) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result)
        } else {
            ResponseEntity.ok(result)
        }
    }
}