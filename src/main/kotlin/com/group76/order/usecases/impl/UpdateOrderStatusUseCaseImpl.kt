package com.group76.order.usecases.impl

import com.group76.order.configuration.SystemProperties
import com.group76.order.entities.OrderStatusEnum
import com.group76.order.entities.request.OrderMessageSnsRequest
import com.group76.order.entities.response.BaseResponse
import com.group76.order.entities.response.GetOrderItemResponse
import com.group76.order.entities.response.GetOrderResponse
import com.group76.order.gateways.IOrderRepository
import com.group76.order.services.ISnsService
import com.group76.order.usecases.IUpdateOrderStatusUseCase
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.*

@Service
class UpdateOrderStatusUseCaseImpl(
    private val orderRepository: IOrderRepository,
    private val snsService: ISnsService,
    private val systemProperties: SystemProperties
) : IUpdateOrderStatusUseCase {
    override fun execute(id: Long, status: OrderStatusEnum): BaseResponse<GetOrderResponse> {
        try {
            if (status == OrderStatusEnum.CANCELLED){
                return BaseResponse(
                    data = null,
                    error = BaseResponse.BaseResponseError("Order can't be cancelled in this method, use DELETE"),
                    statusCodes = HttpStatus.BAD_REQUEST
                )
            }
            val resultFind = orderRepository.findById(id)

            if (resultFind.isEmpty) return BaseResponse(
                data = null,
                error = BaseResponse.BaseResponseError("Order not found"),
                statusCodes = HttpStatus.BAD_REQUEST
            )

            val possibleStatus: Array<OrderStatusEnum> = when (resultFind.get().status) {
                OrderStatusEnum.PENDING -> arrayOf(
                    OrderStatusEnum.RECEIVED
                )
                OrderStatusEnum.CANCELLED -> arrayOf()
                OrderStatusEnum.RECEIVED -> arrayOf(OrderStatusEnum.PREPARATION)
                OrderStatusEnum.PREPARATION -> arrayOf(OrderStatusEnum.READY)
                OrderStatusEnum.READY -> arrayOf(OrderStatusEnum.FINISHED)
                OrderStatusEnum.FINISHED -> arrayOf()
            }

            if (!possibleStatus.contains(status)) {
                return BaseResponse(
                    data = null,
                    statusCodes = HttpStatus.BAD_REQUEST,
                    error = BaseResponse.BaseResponseError("Order status is ${resultFind.get().status} and can't be changed to $status")
                )
            }

            resultFind.get().updateStatus(status)
            val result = orderRepository.save(resultFind.get())
            val snsMessage = OrderMessageSnsRequest(
                orderId = result.id,
                status = result.status,
                clientId = result.clientId
            )

            snsService.publishMessage(
                topicArn = snsService.getTopicArnByName(systemProperties.sns.order)!!,
                message = snsMessage,
                subject = "Order Updated",
                id = result.id.toString()
            )

            if (status == OrderStatusEnum.READY
                || status == OrderStatusEnum.RECEIVED){
                snsService.publishMessage(
                    topicArn = snsService.getTopicArnByName(systemProperties.sns.orderClientNotification)!!,
                    message = snsMessage,
                    subject = "Order Updated",
                    id = result.id.toString()
                )
            }

            if (status == OrderStatusEnum.RECEIVED){
                snsService.publishMessage(
                    topicArn = snsService.getTopicArnByName(systemProperties.sns.orderKitchenNotification)!!,
                    message = snsMessage,
                    subject = "Order Updated",
                    id = result.id.toString()
                )
            }

            return BaseResponse(
                data = GetOrderResponse(
                    createdDate = result.createdDate,
                    status = result.status,
                    clientId = UUID.fromString(result.clientId),
                    id = result.id,
                    totalPrice = result.totalPrice,
                    items = result.items.map {
                        GetOrderItemResponse(
                            productId = UUID.fromString(it.productId),
                            price = it.price,
                            quantity = it.quantity,
                            productName = it.productName
                        )
                    }
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