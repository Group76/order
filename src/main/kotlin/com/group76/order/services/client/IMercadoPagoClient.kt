package com.group76.order.services.client

import com.group76.order.configuration.feign.FeignConfiguration
import com.group76.order.configuration.feign.decoder.FeignExceptionDecoder
import com.group76.order.entities.request.mercadoPago.CreateQrCodeRequest
import com.group76.order.entities.response.CreateQrCodeResponse
import com.group76.order.entities.response.GetMercadoPagoOrderResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "mercadoPago",
    configuration = [FeignConfiguration::class, FeignExceptionDecoder::class])
interface IMercadoPagoClient {
    @PostMapping("/instore/orders/qr/seller/collectors/{userId}/pos/{externalPosId}/qrs")
    fun createQrCode(
        @PathVariable("userId") userId: String,
        @PathVariable("externalPosId") externalPosId: String,
        @RequestBody contract: CreateQrCodeRequest,
        @RequestHeader("Authorization") token: String
    ) : ResponseEntity<CreateQrCodeResponse>

    @GetMapping("/merchant_orders/{id}")
    fun getOrder(
        @PathVariable("id") orderId: String,
        @RequestHeader("Authorization") token: String
    ) : ResponseEntity<GetMercadoPagoOrderResponse>
}