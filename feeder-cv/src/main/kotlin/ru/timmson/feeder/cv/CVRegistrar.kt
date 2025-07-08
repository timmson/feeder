package ru.timmson.feeder.cv

import org.springframework.stereotype.Service
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.cv.model.CVRegisterRequest
import kotlin.math.absoluteValue
import kotlin.math.pow

@Service
class CVRegistrar(
    private val feederConfig: FeederConfig
) {

    private val missedKeywords = listOf(
        "разработчик", "mobile", "net",
        "бэкенд", "системный", "аналитик",
        "системный", "android", "cv", "developer"
    )

    fun parse(request: CVRegisterRequest): CV =
        request.caption.lines().let {
            CV().apply {
                val publicChatId = request.forwardedChatId.absoluteValue - 10.0.pow(12.0).toLong()
                url = listOf(feederConfig.cvChannelUrl, publicChatId, request.forwardedMessageId).joinToString(separator = "/")
                name = parseFileName(request.fileName)
                type = if (feederConfig.cvInnerChannelId == publicChatId) "Внутренний" else it.dropLast(2).last()
                area = if (feederConfig.cvInnerChannelId == publicChatId) feederConfig.cvInnerChannelRegion else it.dropLast(1).last()
                title = it.last().replace("#", "").uppercase()
            }
        }


    internal fun parseFileName(fileName: String) =
        fileName.split(" ", "_", ".", "-").first { it.isNotBlank() && !missedKeywords.contains(it.lowercase()) }

}
