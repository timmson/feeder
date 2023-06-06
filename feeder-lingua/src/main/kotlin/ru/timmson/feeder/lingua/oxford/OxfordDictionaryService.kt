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

    fun explain(word: String): OxfordDictionaryExplainResponse {
        val url = "${feederConfig.oxfordDictionaryUrl}definition/english/$word"

        return oxfordDictionaryClient.fetch(url).let {
            oxfordDictionaryParser.parse(it).let { meanings ->
                OxfordDictionaryExplainResponse(url, meanings)
            }
        }
    }

}
