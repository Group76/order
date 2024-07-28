package com.group76.order.entities.request.mercadoPago

data class QrCodeItemRequest(
    val skuNumber: String,
    val category: String,
    val title: String,
    val description: String,
    val unitPrice: Int,
    val quantity: Int,
    val unitMeasure: String,
    val totalAmount: Int
)
