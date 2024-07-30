package com.group76.order.entities.request

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class CancelOrderRequest (
    @NotNull(message = "Id is required")
    val id: Long,

    @NotEmpty(message = "Reason in required")
    val reason: String
)