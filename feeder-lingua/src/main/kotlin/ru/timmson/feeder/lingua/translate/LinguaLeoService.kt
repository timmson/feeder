package ru.timmson.feeder.lingua.translate

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import ru.timmson.feeder.lingua.translate.model.LinguaLeoTranslationRequest
import ru.timmson.feeder.lingua.translate.model.LinguaLeoTranslationResponse

@Service
class LinguaLeoService(
    private val objectMapper: ObjectMapper,
    private val linguaLeoClient: LinguaLeoClient
) {

    fun translate(word: String): LinguaLeoTranslationResponse =
        translate(LinguaLeoTranslationRequest(word))

    private fun translate(request: LinguaLeoTranslationRequest): LinguaLeoTranslationResponse =
        objectMapper.writeValueAsString(request).let { rawRequest ->
            linguaLeoClient.fetch(rawRequest).let { rawResponse ->
                objectMapper.readValue(rawResponse, LinguaLeoTranslationResponse::class.java).apply {
                    url = if (translate.isNotEmpty()) translate[0].pic_url ?: "" else ""
                }
            }
        }

}
