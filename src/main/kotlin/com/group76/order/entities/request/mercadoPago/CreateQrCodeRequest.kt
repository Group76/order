package com.group76.order.entities.request.mercadoPago

data class CreateQrCodeRequest(
    val externalReference: String,
    val items: List<QrCodeItemRequest>,
    val notificationUrl: String,
    val totalAmount: Double
)
