package ru.timmson.feeder.bot

import com.pengrad.telegrambot.request.SendMessage
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.stereotype.Service
import ru.timmson.feeder.Version
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.common.logger
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
        sendMessageToOwner("Version is ${version}. Started at ${LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)}")
    }

    fun sendMessage(chatId: Any, messageText: String) {
        log.info("Entering sendMessage ($chatId, '$messageText') ...")
        val response = bot.execute(SendMessage(chatId, messageText))
        log.info("Leaving sendMessage (...) = ${response.isOk}")
    }

    fun sendMessageToOwner(messageText: String) {
        sendMessage(feederConfig.ownerId, messageText)
    }

    @PreDestroy
    fun preDestroy() {
        bot.removeGetUpdatesListener()
        bot.shutdown()
    }

}
