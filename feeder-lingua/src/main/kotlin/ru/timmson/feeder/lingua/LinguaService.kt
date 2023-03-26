package ru.timmson.feeder.lingua

import org.springframework.stereotype.Service
import ru.timmson.feeder.lingua.oxford.OxfordDictionaryService
import ru.timmson.feeder.lingua.oxford.model.OxfordDictionaryExplainResponse
import ru.timmson.feeder.lingua.translate.LinguaLeoService
import ru.timmson.feeder.lingua.translate.model.LinguaLeoTranslationResponse

@Service
class LinguaService(
    private val oxfordDictionaryService: OxfordDictionaryService,
    private val linguaLeoService: LinguaLeoService
) {

    fun explain(word: String): OxfordDictionaryExplainResponse =
        oxfordDictionaryService.explain(word)

    fun translate(word: String): LinguaLeoTranslationResponse =
        linguaLeoService.translate(word)


}
