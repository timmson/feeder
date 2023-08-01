package ru.timmson.feeder.service

data class RegisterCVRequest(
    val chatId: String,
    val forwardedMessageId: Int,
    val forwardedMessagedTimeStamp: Int,
    var caption: String,
    val fileName: String
)
