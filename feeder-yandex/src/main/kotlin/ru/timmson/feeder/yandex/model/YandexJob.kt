package ru.timmson.feeder.yandex.model

class YandexJob {
    var strict: List<String> = emptyList()
    var additional: List<String> = emptyList()

    override fun toString(): String = listOf(
        "Основные требования\n\n",
        strict.joinToString("\n"),
        "Дополнительные требования\n\n",
        additional.joinToString("\n")
    ).joinToString()

}
