package org.scobca.checkinhub

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CheckInHubApplication

fun main(args: Array<String>) {
    runApplication<CheckInHubApplication>(*args)
}
