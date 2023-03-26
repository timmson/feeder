package ru.timmson.feeder.lingua

import org.springframework.stereotype.Service
import ru.timmson.feeder.lingua.model.ExplainResponse
import ru.timmson.feeder.lingua.oxford.OxfordDictionaryClient
import ru.timmson.feeder.lingua.oxford.OxfordDictionaryParser

@Service
class LinguaService(
    private val oxfordDictionaryClient: OxfordDictionaryClient,
    private val oxfordDictionaryParser: OxfordDictionaryParser
) {

    fun explain(word: String): ExplainResponse =
        oxfordDictionaryClient.fetch(word).let {
            oxfordDictionaryParser.parse(it)
        }

}
