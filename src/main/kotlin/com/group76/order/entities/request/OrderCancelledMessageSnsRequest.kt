package com.group76.order.entities.request

import com.group76.order.entities.OrderStatusEnum

data class OrderCancelledMessageSnsRequest (
    val orderId: Long,
    val status: OrderStatusEnum = OrderStatusEnum.CANCELLED,
    val reason: String
)