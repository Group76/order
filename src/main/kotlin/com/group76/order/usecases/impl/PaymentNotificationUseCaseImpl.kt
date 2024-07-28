package com.group76.order.usecases.impl

import com.group76.order.entities.MercadoPagoOrderStatusEnum
import com.group76.order.entities.OrderStatusEnum
import com.group76.order.entities.request.CancelOrderRequest
import com.group76.order.entities.request.mercadoPago.PaymentNotificationRequest
import com.group76.order.gateways.IOrderRepository
import com.group76.order.services.IMercadoPagoService
import com.group76.order.usecases.ICancelOrderUseCase
import com.group76.order.usecases.IPaymentNotificationUseCase
import com.group76.order.usecases.IUpdateOrderStatusUseCase
import org.springframework.stereotype.Service

@Service
class PaymentNotificationUseCaseImpl(
    val mercadoPagoService: IMercadoPagoService,
    val orderRepository: IOrderRepository,
    val cancelOrderUseCase: ICancelOrderUseCase,
    val updateOrderStatusUseCase: IUpdateOrderStatusUseCase
): IPaymentNotificationUseCase {
    override fun execute(request: PaymentNotificationRequest) {
        if(request.action != "payment.updated") return

        val response = mercadoPagoService.getOrderInfo(request.id) ?: return

        if(response.second == MercadoPagoOrderStatusEnum.OPENED) return

        val order = orderRepository.findByExternalId(response.first)

        if(response.second == MercadoPagoOrderStatusEnum.CLOSED){
            updateOrderStatusUseCase.execute(
                order.id,
                OrderStatusEnum.RECEIVED
            )
        }
        else if(response.second == MercadoPagoOrderStatusEnum.EXPIRED){
            cancelOrderUseCase.execute(
                request = CancelOrderRequest(
                    id = order.id,
                    reason = "Payment expired"
                )
            )
        }
    }
}