package com.group76.order.configuration.feign.exception

import com.fasterxml.jackson.annotation.JsonProperty

data class ExceptionMessage(
    @JsonProperty("message")
    val message: String,
    @JsonProperty("errors")
    val errors: String?,
    @JsonProperty("requestId")
    val requestId: String?,
    @JsonProperty("timestamp")
    val timestamp: String?
)