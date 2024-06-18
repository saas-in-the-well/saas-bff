package com.saas.bff.Service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest

@Service
class SecretManagerService {
    private val secretsManagerClient = SecretsManagerClient.builder().build()

    fun getSecretValues(secretName: String): Map<String, String> {
        val getSecretValueRequest = GetSecretValueRequest.builder()
            .secretId(secretName)
            .build()

        val getSecretValueResponse = secretsManagerClient.getSecretValue(getSecretValueRequest)
        return getSecretValueResponse.secretString()?.let { secretString ->
            // JSON 데이터를 파싱하여 Map으로 반환
            jacksonObjectMapper().readValue(secretString, Map::class.java) as Map<String, String>
        } ?: emptyMap()
    }
}