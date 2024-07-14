package com.group76.order.controller.v1

import com.group76.order.controller.v1.mapping.UrlMapping
import com.group76.order.entities.OrderStatusEnum
import com.group76.order.entities.request.CreateOrderRequest
import com.group76.order.entities.response.GetOrderResponse
import com.group76.order.usecases.ICreateOrderUseCase
import com.group76.order.usecases.IGetOrderUseCase
import com.group76.order.usecases.IUpdateOrderStatus
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(UrlMapping.Version.V1 + UrlMapping.Resource.ORDER)
class OrderController(
    val createOrderUseCase: ICreateOrderUseCase,
    val getOrderUseCase: IGetOrderUseCase,
    val updateOrderStatus: IUpdateOrderStatus
) {

    @PostMapping(
        name = "CreateOrder"
    )
    @Operation(
        method = "CreateOrder",
        description = "Create a order",
        responses = [
            ApiResponse(
                description = "OK", responseCode = "200", content = [
                    Content(schema = Schema(implementation = GetOrderResponse::class))
                ]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
    fun createOrder(
        @Valid @RequestBody request: CreateOrderRequest
    ): ResponseEntity<Any> {
        val response = createOrderUseCase.execute(request)

        return ResponseEntity(
            response.error ?: response.data,
            response.statusCodes
        )
    }

    @GetMapping(
        name = "GetOrder",
        path = ["{id}"]
    )
    @Operation(
        method = "GetOrder",
        description = "Get a order",
        responses = [
            ApiResponse(
                description = "OK", responseCode = "200", content = [
                    Content(schema = Schema(implementation = GetOrderResponse::class))
                ]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
    fun getOrder(
        @Valid @PathVariable id: Long
    ): ResponseEntity<Any> {
        val response = getOrderUseCase.execute(id)

        return ResponseEntity(
            response.error ?: response.data,
            response.statusCodes
        )
    }

    @PatchMapping(
        name = "GetOrder",
        path = ["{id}/{status}"]
    )
    @Operation(
        method = "UpdateOrderStatus",
        description = "Update order status",
        responses = [
            ApiResponse(
                description = "OK", responseCode = "200", content = [
                    Content(schema = Schema(implementation = GetOrderResponse::class))
                ]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
    fun updateOrder(
        @Valid @PathVariable id: Long,
        @Valid @PathVariable status: OrderStatusEnum
    ): ResponseEntity<Any> {
        val response = updateOrderStatus.execute(id, status)

        return ResponseEntity(
            response.error ?: response.data,
            response.statusCodes
        )
    }
}