package com.group76.order.configuration.feign.exception

import com.group76.order.configuration.feign.decoder.FeignExceptionDecoder
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean

class FeignExceptionDecoderConfig {
    @Bean
    fun decoder(): ErrorDecoder {
        return FeignExceptionDecoder()
    }
}