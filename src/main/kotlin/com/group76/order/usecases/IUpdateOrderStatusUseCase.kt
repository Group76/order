package com.group76.order.usecases

import com.group76.order.entities.OrderStatusEnum
import com.group76.order.entities.response.BaseResponse
import com.group76.order.entities.response.GetOrderResponse

interface IUpdateOrderStatusUseCase {
    fun execute(
        id: Long,
        status: OrderStatusEnum
    ): BaseResponse<GetOrderResponse>
}