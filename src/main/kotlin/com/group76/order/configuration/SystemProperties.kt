package com.group76.order.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "system")
class SystemProperties {
    var idService: String = ""
    val sns: SnsConfiguration = SnsConfiguration()
    val mercadoPagoConfiguration = MercadoPagoConfiguration()

    class SnsConfiguration{
        var order: String = ""
        var orderClientNotification: String = ""
        var orderKitchenNotification: String = ""
    }

    class MercadoPagoConfiguration{
        var userId: String = ""
    }
}

