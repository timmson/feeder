package ru.timmson.feeder.common

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class FeederConfig {

    @Value("\${feeder.stock.timeout}")
    var timeoutInMillis: Long = 0

    val applicationName = "feeder"
}
