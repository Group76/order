package com.group76.order.entities.response

data class CreateQrCodeResponse(
    val qrData: String,
    val inStoreOrderId: String
)
