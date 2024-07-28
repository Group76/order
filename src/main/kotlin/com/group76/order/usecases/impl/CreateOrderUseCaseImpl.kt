package com.group76.order.usecases.impl

import com.group76.order.configuration.SystemProperties
import com.group76.order.entities.OrderEntity
import com.group76.order.entities.OrderItemEntity
import com.group76.order.entities.OrderStatusEnum
import com.group76.order.entities.request.CreateOrderRequest
import com.group76.order.entities.request.OrderMessageSnsRequest
import com.group76.order.entities.response.BaseResponse
import com.group76.order.entities.response.GetOrderItemResponse
import com.group76.order.entities.response.GetOrderResponse
import com.group76.order.gateways.IOrderRepository
import com.group76.order.services.ISnsService
import com.group76.order.usecases.ICreateOrderUseCase
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.*

@Service
class CreateOrderUseCaseImpl(
    private val orderRepository: IOrderRepository,
    private val snsService: ISnsService,
    private val systemProperties: SystemProperties,
) : ICreateOrderUseCase {
    override fun execute(request: CreateOrderRequest): BaseResponse<GetOrderResponse> {
        try {
            val error = request.getError()
            if(error != null) return BaseResponse(
                data = null,
                error = BaseResponse.BaseResponseError(error),
                HttpStatus.BAD_REQUEST
            )

            val order = OrderEntity(
                createdDate = OffsetDateTime.now(),
                totalPrice = 0.0,
                updatedDate = OffsetDateTime.now(),
                status = OrderStatusEnum.PENDING,
                clientId = request.clientId.toString()
            )

            val items = mutableListOf<OrderItemEntity>()
            var totalPrice = 0.0
            request.items.map {
                totalPrice = totalPrice.plus((it.price * it.quantity))

                items.add(
                    OrderItemEntity(
                        order = order,
                        price = it.price,
                        productId = it.productId.toString(),
                        productName = it.productName,
                        quantity = it.quantity
                    )
                )
            }

            order.totalPrice = totalPrice
            order.items = items


            val result = orderRepository.save(order)

            //TODO criar pagamento

            snsService.publishMessage(
                topicArn = snsService.getTopicArnByName(systemProperties.sns.order)!!,
                message = OrderMessageSnsRequest(
                    orderId = result.id,
                    status = result.status,
                    clientId = result.clientId
                ),
                subject = "Order Created",
                id = result.id.toString()
            )

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
        }
        catch (ex: Exception){
            return BaseResponse(
                data = null,
                statusCodes = HttpStatus.INTERNAL_SERVER_ERROR,
                error = BaseResponse.BaseResponseError("Internal server error")
            )
        }
    }
}