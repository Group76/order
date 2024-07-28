package com.group76.order.usecases

import com.group76.order.entities.request.mercadoPago.PaymentNotificationRequest

interface IPaymentNotificationUseCase {
    fun execute(
        request: PaymentNotificationRequest
    )
}