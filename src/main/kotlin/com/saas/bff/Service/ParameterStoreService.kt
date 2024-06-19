package com.saas.bff.Service

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.regions.Regions
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest
import com.example.bff.model.ResponseParameterStoreModel
import com.saas.bff.model.RequestParameterStoreModel
import org.springframework.stereotype.Service

@Service
class ParameterStoreService {

    fun getParameterStoreValue(request: RequestParameterStoreModel): ResponseParameterStoreModel {
        val awsCredentialsProvider: AWSCredentialsProvider = DefaultAWSCredentialsProviderChain()
        val ssmClient = AWSSimpleSystemsManagementClientBuilder.standard()
            .withCredentials(awsCredentialsProvider)
            .withRegion(Regions.AP_NORTHEAST_2) // Replace with your desired AWS region
            .build()

        val getParameterRequest = GetParameterRequest()
            .withName(request.parameterName)
            .withWithDecryption(true)

        val getParameterResult = ssmClient.getParameter(getParameterRequest)
        val responseModel = ResponseParameterStoreModel(getParameterResult.parameter.value)
        return responseModel;
    }
}