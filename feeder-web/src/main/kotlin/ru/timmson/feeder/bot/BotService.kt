package ru.timmson.feeder.bot

interface BotService {

    fun sendMessage(chatId: Any, messageText: String)

    fun sendMessageToOwner(messageText: String)
}