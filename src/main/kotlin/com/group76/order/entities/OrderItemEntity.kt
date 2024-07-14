package com.group76.order.entities

import jakarta.persistence.*

@Entity
@Table(name = "orders_item")
data class OrderItemEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    val order: OrderEntity? = null,

    val productId: String = "",
    val productName: String = "",
    val quantity: Int = 0,
    val price: Double = 0.0
)
