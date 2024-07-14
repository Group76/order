package com.group76.order.entities.response

import com.group76.order.entities.OrderStatusEnum
import java.time.OffsetDateTime
import java.util.*

data class GetOrderResponse(
    val id: Long,
    val clientId: UUID,
    val status: OrderStatusEnum,
    val totalPrice: Double,
    val createdDate: OffsetDateTime,
    val items: List<GetOrderItemResponse>
)
