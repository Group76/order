package com.group76.order.services.client

import com.group76.order.configuration.feign.FeignConfiguration
import com.group76.order.configuration.feign.decoder.FeignExceptionDecoder
import com.group76.order.entities.request.mercadoPago.CreateQrCodeRequest
import com.group76.order.entities.response.CreateQrCodeResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "mercadoPago",
    configuration = [FeignConfiguration::class, FeignExceptionDecoder::class])
interface IMercadoPagoClient {
    @PostMapping("/instore/orders/qr/seller/collectors/{userId}/pos/{externalPosId}/qrs")
    fun createQrCode(
        @PathVariable("userId") userId: String,
        @PathVariable("externalPosId") externalPosId: String,
        @RequestBody contract: CreateQrCodeRequest,
    ) : CreateQrCodeResponse
}