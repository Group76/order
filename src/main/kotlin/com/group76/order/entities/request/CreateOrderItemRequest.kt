package com.group76.order.entities.request

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.util.*

data class CreateOrderItemRequest(
    @NotNull(message = "The product id is required")
    val productId: UUID,
    val quantity: Int,
    val price: Double,

    @NotEmpty(message = "The product name is required.")
    val productName: String
){
    @JsonIgnore
    fun getError(): String? {
        if(quantity <= 0) return "Items quantity is required"
        if(price <= 0) return "Items price is required"
        if(productName.isEmpty()) return "Items name is required"

        return null
    }
}
