package com.group76.order.entities

import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "orders")
data class OrderEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val clientId: String = "",

    @Enumerated(EnumType.STRING)
    val status: OrderStatusEnum = OrderStatusEnum.RECEIVED,

    var totalPrice: Double = 0.0,
    val createdDate: OffsetDateTime = OffsetDateTime.now(),
    val updatedDate: OffsetDateTime = OffsetDateTime.now(),

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    var items: List<OrderItemEntity> = mutableListOf()
)