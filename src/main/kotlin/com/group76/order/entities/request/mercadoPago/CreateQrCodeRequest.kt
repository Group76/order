package com.group76.order.entities.request.mercadoPago

data class CreateQrCodeRequest(
    val cashOut: QrCodeCashOutRequest,
    val description: String,
    val externalReference: String,
    val items: List<QrCodeItemRequest>,
    val notificationUrl: String,
    val sponsor: QrCodeSponsorRequest,
    val title: String,
    val totalAmount: Int
)
