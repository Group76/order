package com.group76.order.entities.request

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.validation.constraints.NotNull
import java.util.*

data class CreateOrderRequest(
    @NotNull(message = "Client id is required")
    val clientId: UUID,

    @NotNull(message = "Items is required")
    val items : List<CreateOrderItemRequest>
){
    @JsonIgnore
    fun getError(): String? {
        if(items.isEmpty()) return "Items is required"

        val errors = items.find { it.getError() != null }
        if (errors != null) return errors.getError()

        return null
    }
}
