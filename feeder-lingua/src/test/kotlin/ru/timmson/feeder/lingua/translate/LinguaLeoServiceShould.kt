package ru.timmson.feeder.lingua.translate

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.eq
import ru.timmson.feeder.lingua.translate.model.LinguaLeoTranslationRequest
import ru.timmson.feeder.lingua.translate.model.LinguaLeoTranslationResponse


@ExtendWith(MockitoExtension::class)
class LinguaLeoServiceShould {

    private lateinit var linguaLeoService: LinguaLeoService

    @Mock
    private lateinit var objectMapper: ObjectMapper

    @Mock
    private lateinit var linguaLeoClient: LinguaLeoClient

    @BeforeEach
    fun setUp() {
        linguaLeoService = LinguaLeoService(objectMapper, linguaLeoClient)
    }

    @Test
    fun parse() {
        val expected = LinguaLeoTranslationResponse()
        val word = "profit"
        val request = LinguaLeoTranslationRequest(word)
        val rawRequest = "{ - }"
        val rawResponse = "{ + }"

        `when`(objectMapper.writeValueAsString(eq(request))).thenReturn(rawRequest)
        `when`(linguaLeoClient.fetch(eq(rawRequest))).thenReturn(rawResponse)
        `when`(objectMapper.readValue(eq(rawResponse), eq(LinguaLeoTranslationResponse::class.java))).thenReturn(expected)
        val actual = linguaLeoService.translate(word)

        assertEquals(expected, actual)
    }
}

