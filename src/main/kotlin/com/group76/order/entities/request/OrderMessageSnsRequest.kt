package com.group76.order.entities.request

import com.group76.order.entities.OrderStatusEnum

data class OrderMessageSnsRequest (
    val orderId: Long,
    val status: OrderStatusEnum
)