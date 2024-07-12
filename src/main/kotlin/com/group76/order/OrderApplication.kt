package com.group76.order

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class OrderApplication

fun main(args: Array<String>) {
	runApplication<OrderApplication>(*args)
}
