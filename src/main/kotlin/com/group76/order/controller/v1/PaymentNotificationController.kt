package com.group76.order.controller.v1

import com.group76.order.controller.v1.mapping.UrlMapping
import com.group76.order.entities.request.mercadoPago.PaymentNotificationRequest
import com.group76.order.entities.response.GetOrderResponse
import com.group76.order.usecases.IPaymentNotificationUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(UrlMapping.Version.V1 + UrlMapping.Resource.PAYMENT)
class PaymentNotificationController(
    val paymentNotificationUseCase: IPaymentNotificationUseCase
) {
    @PostMapping(
        name = "Notification",
        path = ["/notification"]
    )
    @Operation(
        method = "Payment Notification",
        description = "Payment Notification",
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
    fun paymentNotification(
        @Valid @RequestBody request: PaymentNotificationRequest
    ): ResponseEntity<Any> {
        paymentNotificationUseCase.execute(request)
        return ResponseEntity.ok().build()
    }
}