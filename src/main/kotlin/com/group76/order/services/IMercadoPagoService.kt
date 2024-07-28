package com.group76.order.services

import com.group76.order.entities.MercadoPagoOrderStatusEnum
import com.group76.order.entities.OrderEntity
import com.group76.order.entities.response.CreateQrCodeResponse

interface IMercadoPagoService {
    fun createQrCode(order: OrderEntity): CreateQrCodeResponse?
    fun getOrderInfo(orderId: String): Pair<String, MercadoPagoOrderStatusEnum>?
}