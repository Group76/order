package com.group76.order.usecases

import com.group76.order.entities.request.CancelOrderRequest
import com.group76.order.entities.response.BaseResponse
import com.group76.order.entities.response.CancelResponse

interface ICancelOrderUseCase {
    fun execute(
        request: CancelOrderRequest
    ): BaseResponse<CancelResponse>
}