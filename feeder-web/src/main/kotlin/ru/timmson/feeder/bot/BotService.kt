package ru.timmson.feeder.bot

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.stereotype.Service
import ru.timmson.feeder.Version
import ru.timmson.feeder.bot.model.request.SendMessage
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.common.logger
import java.time.LocalDateTime

@Service
class BotService(
    private val bot: BotProxy,
    private val feederConfig: FeederConfig,
    private val botListener: BotListener,
    private val version: Version
) {

    private val log = logger<BotService>()

    @PostConstruct
    fun postConstruct() {
        bot.startup(feederConfig.token)
        bot.setUpdatesListener(botListener)
        sendMessageToOwner("Version is ${version}. Started at ${LocalDateTime.now()}")
    }

    fun sendMessage(chatId: Any, messageText: String) =
        sendMessage(SendMessage(chatId, messageText, true))

    fun sendMessage(message: SendMessage) {
        log.info("Entering sendMessage (${message.chatId}, '${message.text.take(50)}') ...")
        val response = bot.execute(message)
        log.info("Leaving sendMessage (...) = ${response.isOk}")
    }

    fun sendMessageToOwner(messageText: String) {
        sendMessage(feederConfig.ownerId, messageText)
    }

    fun downloadFile(fileId: String): ByteArray =
        bot.downloadFile(fileId)

    @PreDestroy
    fun preDestroy() {
        bot.removeGetUpdatesListener()
        bot.shutdown()
    }

}
