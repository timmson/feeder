package ru.timmson.feeder.lingua.oxford

import org.springframework.stereotype.Service
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.lingua.oxford.model.OxfordDictionaryExplainResponse

@Service
class OxfordDictionaryService(
    private val feederConfig: FeederConfig,
    private val oxfordDictionaryClient: OxfordDictionaryClient,
    private val oxfordDictionaryParser: OxfordDictionaryParser
) {

    private fun createUrl(word: String): String = "${feederConfig.oxfordDictionaryUrl}definition/english/$word"

    fun explain(word: String): OxfordDictionaryExplainResponse {
        val url = createUrl(word)

        return oxfordDictionaryClient.fetch(url).let {
            oxfordDictionaryParser.parse(it).let { meanings ->
                OxfordDictionaryExplainResponse(url, meanings)
            }
        }
    }

}
