package ru.timmson.feeder.bot

import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.request.SendMessage
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Value
import ru.timmson.feeder.common.logger


//@Service
class Main(
    @Value("\${feeder.tg.token}") private val token: String,
    @Value("\${feeder.tg.owner.id}") private val ownerId: String
) {

    private val log = logger<Main>()

    private lateinit var bot: TelegramBot

    @PostConstruct
    fun postConstruct() {
        bot = TelegramBot(token)

        log.info("Sending hello message ...")

        val helloMessage = SendMessage(ownerId, "Hello!")
        val response = bot.execute(helloMessage)

        log.info("Response: $response")
    }

    @PreDestroy
    fun preDestroy() {
        bot.shutdown()
    }

}