package ru.timmson.feeder.bot

import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.request.SendMessage
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.stereotype.Service
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.common.logger
import java.time.LocalDateTime


@Service
class BotServiceImpl(
    private val feederConfig: FeederConfig,
    private val botListener: BotListener
) : BotService {

    private val log = logger<BotServiceImpl>()

    private lateinit var bot: TelegramBot

    @PostConstruct
    fun postConstruct() {
        bot = TelegramBot(feederConfig.token)
        bot.setUpdatesListener(botListener)
        sendMessageToOwner("Started at ${LocalDateTime.now()}")
    }

    override fun sendMessage(chatId: Any, messageText: String) {
        log.info("Entering sendMessage ($chatId, '$messageText') ...")
        val response = bot.execute(SendMessage(chatId, messageText))
        log.info("Leaving sendMessage (...) = ${response.isOk}")
    }

    override fun sendMessageToOwner(messageText: String) {
        sendMessage(feederConfig.ownerId, messageText)
    }

    @PreDestroy
    fun preDestroy() {
        bot.removeGetUpdatesListener()
        bot.shutdown()
    }

}