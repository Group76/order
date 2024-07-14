package com.group76.order.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "system")
class SystemProperties {
    var idService: String = ""
    val collection: CollectionConfiguration = CollectionConfiguration()
    val sns: SnsConfiguration = SnsConfiguration()

    class CollectionConfiguration{
        var order: String = ""
    }

    class SnsConfiguration{
        var order: String = ""
    }
}

