package com.group76.order.usecases.impl

import com.group76.order.configuration.SystemProperties
import com.group76.order.entities.OrderStatusEnum
import com.group76.order.entities.response.BaseResponse
import com.group76.order.entities.response.GetOrderResponse
import com.group76.order.gateways.IOrderRepository
import com.group76.order.services.ISnsService
import com.group76.order.usecases.IUpdateOrderStatus
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class UpdateOrderStatusImpl(
    private val orderRepository: IOrderRepository,
    private val snsService: ISnsService,
    private val systemProperties: SystemProperties
): IUpdateOrderStatus {
    override fun execute(id: Long, status: OrderStatusEnum): BaseResponse<GetOrderResponse> {
        val result = orderRepository.findById(id)

        if (result.isEmpty) return BaseResponse(
            data = null,
            error = BaseResponse.BaseResponseError("Order not found"),
            statusCodes = HttpStatus.BAD_REQUEST
        )


    }
}