package ru.timmson.feeder.common

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class FeederConfig {
    @Value("\${feeder.tg.token}")
    var token: String = ""

    @Value("\${feeder.tg.owner.id}")
    var ownerId: String = ""

    @Value("\${feeder.stock.schedule}")
    var scheduleStock: String = ""

    @Value("\${feeder.stock.timeout}")
    var timeoutInMillis: Long = 0

}