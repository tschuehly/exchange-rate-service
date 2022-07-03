package de.tschuehly.exchangerateservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ExchangeRateServiceApplication

fun main(args: Array<String>) {
	runApplication<ExchangeRateServiceApplication>(*args)
}
