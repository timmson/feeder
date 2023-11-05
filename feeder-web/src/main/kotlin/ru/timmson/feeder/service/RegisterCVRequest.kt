package ru.timmson.feeder.service

data class RegisterCVRequest(
    val chatId: Long,
    val forwardedChatId: Long,
    val forwardedMessageId: Int,
    val forwardedMessagedDate: String,
    var caption: String,
    val fileName: String
)
