package ru.timmson.feeder.bot.model.request

data class SendMessage(
    val chatId: Any,
    val text: String
)
