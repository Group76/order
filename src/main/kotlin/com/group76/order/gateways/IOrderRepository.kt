package com.group76.order.gateways

import com.group76.order.entities.OrderEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface IOrderRepository: CrudRepository<OrderEntity, Long> {
}