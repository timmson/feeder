package ru.timmson.feeder.cv

data class CVRegisterRequest(
    val forwardedChatId: Long,
    val forwardedMessageId: Int,
    val caption: String,
    val fileName: String
)
