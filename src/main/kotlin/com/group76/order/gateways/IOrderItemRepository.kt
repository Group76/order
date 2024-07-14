package com.group76.order.gateways

import com.group76.order.entities.OrderItemEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface IOrderItemRepository: CrudRepository<OrderItemEntity, UUID> {
}