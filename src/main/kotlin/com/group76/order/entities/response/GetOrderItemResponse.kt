package com.group76.order.entities.response

import java.util.*

data class GetOrderItemResponse (
    val productId: UUID,
    val productName: String,
    val quantity: Int,
    val price: Double,
)