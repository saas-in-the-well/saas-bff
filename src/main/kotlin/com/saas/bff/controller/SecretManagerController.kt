package com.saas.bff.controller

import com.saas.bff.Service.SecretManagerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/secrets")
class SecretManagerController(
    private val secretManagerService: SecretManagerService
) {
    @GetMapping("/{secretName}")
    fun getSecretValues(@PathVariable secretName: String): ResponseEntity<Map<String, String>> {
        val secretValues = secretManagerService.getSecretValues(secretName)
        return ResponseEntity.ok(secretValues)
    }
}