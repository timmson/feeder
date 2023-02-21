package ru.timmson.feeder.bot

import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.request.SendMessage
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.timmson.feeder.common.logger
import java.time.LocalDateTime


@Service
class BotServiceImpl(
    @Value("\${feeder.tg.token}") private val token: String,
    @Value("\${feeder.tg.owner.id}") private val ownerId: String
) : BotService {

    private val log = logger<BotServiceImpl>()

    private lateinit var bot: TelegramBot

    @PostConstruct
    fun postConstruct() {
        bot = TelegramBot(token)

        sendMessage("Started at ${LocalDateTime.now()}")
    }

    override fun sendMessage(messageText: String) {
        log.info("Entering sendMessage(\"$messageText\") ...")
        val helloMessage = SendMessage(ownerId, messageText)
        val response = bot.execute(helloMessage)
        log.info("Leaving sendMessage(...) = $response")
    }

    @PreDestroy
    fun preDestroy() {
        bot.shutdown()
    }

}