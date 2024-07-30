package com.group76.order.usecases

import com.group76.order.entities.request.CreateOrderRequest
import com.group76.order.entities.response.BaseResponse
import com.group76.order.entities.response.GetOrderResponse

interface ICreateOrderUseCase {
    fun execute(
        request: CreateOrderRequest
    ) : BaseResponse<GetOrderResponse>
}