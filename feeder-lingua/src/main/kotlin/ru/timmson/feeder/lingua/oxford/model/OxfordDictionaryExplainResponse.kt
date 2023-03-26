package ru.timmson.feeder.lingua.oxford.model

data class OxfordDictionaryExplainResponse(
    val url: String,
    val meanings: List<Meaning>
)
