package ru.timmson.feeder.lingua.oxford

import org.jsoup.Jsoup
import org.springframework.stereotype.Service
import ru.timmson.feeder.lingua.model.ExplainResponse
import ru.timmson.feeder.lingua.model.Meaning

@Service
class OxfordDictionaryParser {

    fun parse(source: String): ExplainResponse {

        val meanings = Jsoup.parse(source).run {
            select("span.def").eachText().map { Meaning(it) }
        }

        return ExplainResponse(meanings)
    }

}
