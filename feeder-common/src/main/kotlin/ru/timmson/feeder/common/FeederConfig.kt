package ru.timmson.feeder.common

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class FeederConfig {

    val airtableToken: String = ""
    val airtableUrl: String = ""

    @Value("\${feeder.tg.token}")
    var token: String = ""

    @Value("\${feeder.tg.owner.id}")
    var ownerId: String = ""

    @Value("\${feeder.stock.schedule}")
    var scheduleStock: String = ""

    @Value("\${feeder.stock.timeout}")
    var timeoutInMillis: Long = 0

    @Value("\${feeder.stock.channelId}")
    var stockChannelId: String = ""

    @Value("\${feeder.lingua.oxfordDictionaryUrl}")
    var oxfordDictionaryUrl: String = ""

    @Value("\${feeder.lingua.linguaLeoUrl}")
    var linguaLeoUrl: String = ""

    @Value("\${feeder.cv.channelUrl}")
    var cvChannelUrl: String = ""

}
