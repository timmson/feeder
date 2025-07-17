package ru.timmson.feeder.common

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class FeederConfig {

    @Value("\${feeder.tg.token}")
    var token: String = ""

    @Value("\${feeder.tg.owner.id}")
    var ownerId: String = ""

    @Value("\${feeder.tg.users}")
    var users: List<String> = emptyList()

    @Value("\${feeder.stock.schedule}")
    var scheduleStock: String = ""

    @Value("\${feeder.stock.cacheTTL}")
    var scheduleCacheTTL: String = ""

    @Value("\${feeder.stock.timeout}")
    var timeoutInMillis: Long = 0

    @Value("\${feeder.stock.channelId}")
    var stockChannelId: String = ""

    @Value("\${feeder.cv.channelUrl}")
    var cvChannelUrl: String = ""

    @Value("\${feeder.cv.innerChannelId}")
    var cvInnerChannelId: Long = 0

    @Value("\${feeder.cv.innerChannelRegion}")
    var cvInnerChannelRegion: String = ""

    @Value("\${feeder.spreadSheet.enable}")
    var spreadSheatEnable: String = ""

    val isSpreadSheatEnabled: Boolean
        get() = spreadSheatEnable == "true"

    @Value("\${feeder.spreadSheet.id}")
    var spreadSheetId: String = ""

    @Value("\${feeder.spreadSheet.secretFile}")
    var spreadSheetSecretFile: String = ""

    @Value("\${feeder.yandex.enable}")
    var yandexEnable: String = ""

    val isYandexEnabled: Boolean
        get() = yandexEnable == "true"

    @Value("\${feeder.yandex.token}")
    var yandexToken: String = ""

    @Value("\${feeder.yandex.model}")
    var yandexModel: String = ""

    val applicationName = "feeder"
}
