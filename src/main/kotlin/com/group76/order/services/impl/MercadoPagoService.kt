package com.group76.order.services.impl

import com.group76.order.configuration.SystemProperties
import com.group76.order.entities.MercadoPagoOrderStatusEnum
import com.group76.order.entities.OrderEntity
import com.group76.order.entities.request.mercadoPago.CreateQrCodeRequest
import com.group76.order.entities.request.mercadoPago.QrCodeItemRequest
import com.group76.order.entities.response.CreateQrCodeResponse
import com.group76.order.services.IMercadoPagoService
import com.group76.order.services.client.IMercadoPagoClient
import org.springframework.stereotype.Service

@Service
class MercadoPagoService(
    val systemProperties: SystemProperties,
    val client: IMercadoPagoClient
): IMercadoPagoService {
    override fun createQrCode(order: OrderEntity): CreateQrCodeResponse? {
        val result = client.createQrCode(
            userId = systemProperties.mercadoPagoConfiguration.userId,
            token = "Bearer ${systemProperties.mercadoPagoConfiguration.testToken}",
            externalPosId = systemProperties.mercadoPagoConfiguration.externalPosId,
            contract = CreateQrCodeRequest(
                items = order.items.map {
                    QrCodeItemRequest(
                        totalAmount = it.quantity * it.price,
                        quantity = it.quantity,
                        unitPrice = it.price
                    )
                },
                externalReference = order.externalId,
                notificationUrl = "https://t-tozatto.com/order/v1/payment/notification",
                totalAmount = order.totalPrice
            )
        )

        return result.body
    }

    override fun getOrderInfo(orderId: String): Pair<String, MercadoPagoOrderStatusEnum>? {
        val response = client.getOrder(
            orderId = orderId,
            token = "Bearer ${systemProperties.mercadoPagoConfiguration.testToken}"
        )

        val body = response.body ?: return null

        return when (body.status) {
            "opened" -> body.externalReference to MercadoPagoOrderStatusEnum.OPENED
            "closed" -> body.externalReference to MercadoPagoOrderStatusEnum.CLOSED
            "expired" -> body.externalReference to MercadoPagoOrderStatusEnum.EXPIRED
            else -> null
        }
    }
}