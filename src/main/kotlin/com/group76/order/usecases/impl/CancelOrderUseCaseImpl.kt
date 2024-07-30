package com.group76.order.usecases.impl

import com.group76.order.configuration.SystemProperties
import com.group76.order.entities.OrderStatusEnum
import com.group76.order.entities.request.CancelOrderRequest
import com.group76.order.entities.request.OrderCancelledMessageSnsRequest
import com.group76.order.entities.request.OrderMessageSnsRequest
import com.group76.order.entities.response.BaseResponse
import com.group76.order.entities.response.CancelResponse
import com.group76.order.gateways.IOrderRepository
import com.group76.order.services.ISnsService
import com.group76.order.usecases.ICancelOrderUseCase
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class CancelOrderUseCaseImpl(
    private val orderRepository: IOrderRepository,
    private val snsService: ISnsService,
    private val systemProperties: SystemProperties
): ICancelOrderUseCase {
    override fun execute(request: CancelOrderRequest): BaseResponse<CancelResponse> {
        try {
            val resultFind = orderRepository.findById(request.id)

            if (resultFind.isEmpty) return BaseResponse(
                data = null,
                error = BaseResponse.BaseResponseError("Order not found"),
                statusCodes = HttpStatus.BAD_REQUEST
            )

            if (resultFind.get().status != OrderStatusEnum.PENDING) {
                return BaseResponse(
                    data = null,
                    statusCodes = HttpStatus.BAD_REQUEST,
                    error = BaseResponse.BaseResponseError("Order status is ${resultFind.get().status} and can't be cancelled")
                )
            }

            resultFind.get().cancelled(request.reason)
            val result = orderRepository.save(resultFind.get())
            val messageSns = OrderMessageSnsRequest(
                orderId = result.id,
                status = result.status,
                description = result.cancelledReason,
                clientId = result.clientId
            )

            snsService.publishMessage(
                topicArn = snsService.getTopicArnByName(systemProperties.sns.order)!!,
                message = messageSns,
                subject = "Order Cancelled",
                id = result.id.toString()
            )

            snsService.publishMessage(
                topicArn = snsService.getTopicArnByName(systemProperties.sns.orderClientNotification)!!,
                message = messageSns,
                subject = "Order Cancelled",
                id = result.id.toString()
            )

            return BaseResponse(
                data = CancelResponse(
                    success = true
                ),
                error = null
            )
        } catch (ex: Exception) {
            return BaseResponse(
                data = null,
                statusCodes = HttpStatus.INTERNAL_SERVER_ERROR,
                error = BaseResponse.BaseResponseError("Internal server error")
            )
        }
    }
}