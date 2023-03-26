package ru.timmson.feeder.lingua.oxford

import org.jsoup.Jsoup
import org.springframework.stereotype.Service
import ru.timmson.feeder.lingua.oxford.model.Meaning

@Service
class OxfordDictionaryParser {

    fun parse(source: String): List<Meaning> =
        Jsoup.parse(source).run {
            select("span.def").eachText().map { Meaning(it) }
        }

}
