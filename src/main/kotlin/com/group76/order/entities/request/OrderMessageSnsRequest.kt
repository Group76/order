package com.group76.order.entities.request

import com.group76.order.entities.OrderStatusEnum

data class OrderMessageSnsRequest (
    val orderId: Long,
    val clientId: String,
    val status: OrderStatusEnum,
    val description: String? = null
)