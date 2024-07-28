package com.group76.order.entities.request.mercadoPago

data class QrCodeItemRequest(
    val unitPrice: Double,
    val quantity: Int,
    val unitMeasure: String = "unit",
    val totalAmount: Double
)
