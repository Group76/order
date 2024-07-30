package com.group76.order.configuration.feign

import feign.Logger
import feign.Retryer
import feign.okhttp.OkHttpClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackages = ["com.group76.order.services"])
class FeignConfiguration {

    @Bean
    fun feignLoggerLevel(): Logger.Level? {
        return Logger.Level.FULL
    }

    @Bean
    fun retryer(): Retryer {
        return Retryer.Default(500, 1000, 3)
    }

    @Bean
    fun client(): OkHttpClient? {
        //Necessário para habilitar os http methods PATCH, PUT e DELETE
        //https://www.baeldung.com/openfeign-http-patch-request
        return OkHttpClient()
    }
}