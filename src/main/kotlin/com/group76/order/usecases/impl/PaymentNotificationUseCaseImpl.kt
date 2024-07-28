package com.group76.order.usecases.impl

import com.group76.order.entities.request.mercadoPago.PaymentNotificationRequest
import com.group76.order.usecases.IPaymentNotificationUseCase
import org.springframework.stereotype.Service

@Service
class PaymentNotificationUseCaseImpl: IPaymentNotificationUseCase {
    override fun execute(request: PaymentNotificationRequest) {
        TODO("Not yet implemented")
    }
}