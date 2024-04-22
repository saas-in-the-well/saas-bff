package com.example.bff.model

data class ResponseModel(
    val id: Long,
    val name: String,
    var data: Map<String, Any>
)


