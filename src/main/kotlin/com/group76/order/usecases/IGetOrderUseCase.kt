package com.group76.order.usecases

import com.group76.order.entities.request.CreateOrderRequest
import com.group76.order.entities.response.BaseResponse
import com.group76.order.entities.response.GetOrderResponse

interface IGetOrderUseCase {
    fun execute(
        id: Long
    ) : BaseResponse<GetOrderResponse>
}