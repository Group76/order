package com.group76.order.entities.request.mercadoPago

data class PaymentNotificationRequest(
    val action: String,
    val apiVersion: String,
    val data: PaymentNotificationDataRequest,
    val dateCreated: String,
    val id: String,
    val liveMode: Boolean,
    val type: String,
    val userId: Long
)
